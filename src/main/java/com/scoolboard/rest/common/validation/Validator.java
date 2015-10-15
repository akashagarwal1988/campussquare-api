package com.scoolboard.rest.common.validation;

import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;

public abstract class Validator<T, M, A extends Annotation> {

    @Setter
    @Getter
    private List<Object> parents = new ArrayList<>();
    @Setter
    @Getter
    private List<Object> contexts = new ArrayList<>();

    Class<?> typeT;
    Class<?> typeM;

    public Validator() {
        Class<?> superclass = this.getClass();
        while (superclass.getSuperclass() != Validator.class) {
            superclass = getClass().getSuperclass();
        }
        ParameterizedType generics = (ParameterizedType) superclass.getGenericSuperclass();
        this.typeT = (Class<?>)unwrapParameterizedType(generics.getActualTypeArguments()[0]);
        this.typeM = (Class<?>)unwrapParameterizedType(generics.getActualTypeArguments()[1]);
    }
    
    public abstract Optional<Message> validate(T value, M model, A annotation);

    @SuppressWarnings("unchecked")
    public Optional<Message> validateAny(Object value, Object model, A annotation, List<Object> parents, List<Object> contexts) {
        if (value != null && !typeT.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException(String.format("Validator %s is expected to handle values of type %s but found %s", getClass().getName(), typeT.getName(), value.getClass().getName()));
        }

        if (model != null && !typeM.isAssignableFrom(model.getClass())) {
            throw new IllegalArgumentException(String.format("Validator %s is expected to handle models of type %s but found %s", getClass().getName(), typeM.getName(), model.getClass().getName()));
        }

        this.parents = parents;
        this.contexts = contexts;

        return validate((T) value, (M) model, annotation);
    }

    protected Optional<Message> validateRequired(Object value, boolean required, String customMessage) {

        Function<String, Optional<Message>> message = (msg) -> Optional.of(new Message("property.required", getMessage(msg, Optional.of(customMessage))));

        if (value instanceof Optional<?>) {
            Optional<?> option = (Optional<?>) value;
            if (option.isPresent() && option.get() != null) value = option.get();
            else if (required)
                return message.apply("This field is required.");
            else return Optional.empty();
        }

        if ((value == null || (value instanceof String && ((String) value).isEmpty())) && required) {
            return message.apply("This field is required.");
        } else return Optional.empty();
    }

    protected String getMessage(String defaultMessage, Optional<String> custom) {
        return custom.orElse("").isEmpty() ? defaultMessage : custom.get();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getContext(Class<C> cls) {
        Optional<C> match = ((Optional<C>) contexts.stream().filter(c -> cls.isAssignableFrom(c.getClass())).findFirst());
        if (match.isPresent()) return match.get();
        else
            throw new RuntimeException(String.format("Cannot find validation context for type %s in validator %s ", cls.getName(), getClass().getName()));
    }

    @SuppressWarnings("unchecked")
    protected <C> C getParent(Class<C> cls) {
        Optional<C> match = ((Optional<C>) parents.stream().filter(c -> cls.isAssignableFrom(c.getClass())).findFirst());
        if (match.isPresent()) return match.get();
        else
            throw new RuntimeException(String.format("Cannot find parent for type %s in validator %s ", cls.getName(), getClass().getName()));
    }

    protected Optional<Message> createMessage(String message) {
        return Optional.of(new Message("custom", getMessage(message, Optional.of(message))));
    }

    protected Type unwrapParameterizedType(Type t) {
    	while (t instanceof ParameterizedType) {
    		t = ((ParameterizedType)t).getRawType();
    	}
    	return t;
    }

    public static void throwValidationMessage(String message, Optional<String> propertyName){
        List<ValidationMessage> errors = new ArrayList<>();
        ValidationMessage msg = new ValidationMessage();
        Map<String, List<ValidationMessage>> aMap = new HashMap<String, List<ValidationMessage>>();
        msg.setMessage(message);
        msg.setPropertyName(propertyName);
        errors.add(msg);
        aMap.put("validationMessages", errors);
        throw new WebApplicationException(Response.status(422).entity(aMap).build());
    }

}
