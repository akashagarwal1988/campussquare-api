package com.scoolboard.rest.common.validation;

import com.scoolboard.rest.common.data.HasId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by akasha on 1/30/15.
 */
@Component
public class ValidationImpl implements Validation {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private static ConcurrentHashMap<String, ClassValidationInfo> cache = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Optional<Validator<?, ?, Annotation>>> validatorsCache = new ConcurrentHashMap<>();

    @Override
    public <T> List<ValidationMessage> validate(T model) throws Exception {
        return validateInternal(model, new ArrayList<>(), new ArrayList<>());
    }

    private <T> List<ValidationMessage> validateInternal(T model, List<Object> parents, List<Object> context) throws RuntimeException {
        if (model == null) return new ArrayList<>();
        else if (model instanceof Collection<?>) {
            return ((Collection<T>) model).stream().flatMap((x) -> validateInternal(x, parents, context).stream()).collect(Collectors.toList());
        } else if (model instanceof Optional<?>) return validateSingle(((Optional<?>) model).get(), parents, context);
        else return validateSingle(model, parents, context);
    }

    @SuppressWarnings("unchecked")
    private <T> List<ValidationMessage> validateSingle(T model, List<Object> parents, List<Object> context) throws RuntimeException {
        Class cls = model.getClass();
        List<ValidationMessage> result = new ArrayList<>();
        ClassValidationInfo info = extractValidators(cls);
        if (!info.getClassValidators().isEmpty() || !info.getFieldValidators().isEmpty()) {

            Optional<Long> id;

            if (model instanceof HasId && ((HasId) model).getId() != null)
                id = Optional.of(Long.parseLong(((HasId) model).getId().toString()));
            else id = Optional.empty();

            List<Object> newContexts = new ArrayList<>();
            newContexts.addAll(context);

            if (cls.isAnnotationPresent(ValidationContext.class)) {
                Class valContextCls = ((ValidationContext) cls.getAnnotation(ValidationContext.class)).clazz();
                try {
                    HasValidationContext<T> validationContext = beanFactory.getBean((Class<HasValidationContext<T>>) valContextCls);
                    newContexts.add(0, validationContext.getValidationContext(model));
                } catch (ClassCastException e) {
                    throw new RuntimeException(String.format("Context attribute for class %s found pointing to validation context %s however it does not implement %s.", cls.getName(), valContextCls.getName(), HasValidationContext.class.getName()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            //execute class validators
            info.getClassValidators().forEach(v -> executeValidator(v.getValidator(), v.getAnnotation(), Optional.empty(), model, model, parents, newContexts, result, id));

            //execute field validators
            info.getFieldValidators().forEach((p, validators) -> {
                if (!validators.isEmpty()) {
                    p.setAccessible(true);
                    Object value;
                    try {
                        value = p.get(model);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    validators.forEach(v -> {
                        //the validation attribute must implement the ValidatorClass which connects it to a Scala Validator
                        if (v.getAnnotation() instanceof Nested) {
                            List<Object> newParents = new ArrayList<>();
                            newParents.add(0, model);
                            newParents.addAll(parents);
                            result.addAll(validateInternal(value, newParents, newContexts));
                        } else
                            executeValidator(v.getValidator(), v.getAnnotation(), Optional.of(p), value, model, parents, newContexts, result, id);
                    });
                }
            });
        }
        return result;
    }

    private ClassValidationInfo extractValidators(Class cls) {
        String key = cls.getName();
        if (!cache.containsKey(key)) {
            ClassValidationInfo info = new ClassValidationInfo();
            if (cls.isAnnotationPresent(CustomArray.class)) {
                CustomArray annotation = (CustomArray) cls.getAnnotation(CustomArray.class);
                Arrays.asList(annotation.value()).forEach(c -> getValidator(c.annotationType()).map(v -> info.getClassValidators().add(new ValidatorInfo(c, v))));
            }

            if (cls.isAnnotationPresent(Custom.class)) {
                Annotation annotation = cls.getAnnotation(Custom.class);
                getValidator(annotation.annotationType()).map(v -> info.getClassValidators().add(new ValidatorInfo(annotation, v)));
            }

            Arrays.asList(cls.getDeclaredFields()).forEach(p -> {

                List<ValidatorInfo> validators = new ArrayList<>();

                Arrays.asList(p.getDeclaredAnnotations()).forEach(a -> {
                    //the validation attribute must implement the ValidatorClass which connects it to a Scala Validator
                    if (a instanceof Nested) {
                        validators.add(new ValidatorInfo(a, null));
                    } else if (a instanceof CustomArray) {
                        Arrays.asList(((CustomArray) a).value()).forEach(an -> getValidator(an.annotationType()).map(v -> validators.add(new ValidatorInfo(an, v))));
                    } else
                        getValidator(a.annotationType()).map(v -> validators.add(new ValidatorInfo(a, v)));
                });

                info.getFieldValidators().put(p, validators);
            });

            cache.putIfAbsent(key, info);

            return info;
        } else return cache.get(key);
    }

    private ValidationMessage createValidationMessage(Message
                                                              msg, Optional<String> fieldName, Optional<Class> modelClass, Optional<Long> id) {
        //translate here
        String messageStr = msg.getArgs().isEmpty() ? msg.getKey() : msg.getArgs().get(0).toString();
        ValidationMessage message = new ValidationMessage();
        message.setMessage(messageStr);
        message.setKey(msg.getKey());
        message.setObjectName(modelClass.map(Class::getSimpleName));
        message.setPropertyName(fieldName);
        message.setId(id);
        return message;
    }

    private <A extends Annotation> void executeValidator(Validator<?, ?, A> validator,
                                                         A annotation, Optional<Field> field,
                                                         Object value,
                                                         Object model,
                                                         List<Object> parents,
                                                         List<Object> newContexts,
                                                         List<ValidationMessage> messages,
                                                         Optional<Long> id) {
        try {
            Optional<Message> error = validator.validateAny(value, model, annotation, parents, newContexts);

            if (error != null && error.isPresent()) {
                messages.add(createValidationMessage(error.get(), field.map(Field::getName), Optional.of(model.getClass()), id));
            }

        } catch (ClassCastException e) {
            String property;
            if (field.isPresent()) property = String.format(" for property %s", field.get().getName());
            else property = "";
            throw new RuntimeException(String.format("Validation attribute has been applied to the wrong value type of model %s %s", model.getClass().getName(), property), e);
        } finally {
            validator.setContexts(new ArrayList<>());
            validator.setParents(new ArrayList<>());
        }

    }

    @SuppressWarnings("unchecked")
    private Optional<Validator<?, ?, Annotation>> getValidator(Class<? extends Annotation> annotationType) {

        String key = annotationType.getName();
        if (validatorsCache.containsKey(key)) {
            return validatorsCache.get(key);
        } else {
            ValidatorClass valAnnotation = annotationType.getAnnotation(ValidatorClass.class);
            Optional<Validator<?, ?, Annotation>> validator = Optional.empty();
            //if all good get the Validator
            if (valAnnotation != null) {
                validator = Optional.of((Validator<?, ?, Annotation>) beanFactory.getBean(valAnnotation.clazz()));
            }

            validatorsCache.put(key, validator);

            return validator;
        }
    }
}

