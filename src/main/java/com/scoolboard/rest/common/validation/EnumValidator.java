package com.scoolboard.rest.common.validation;

import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by akasha on 2/4/15.
 */
@Component
public class EnumValidator extends Validator<Object, Object, EnumType> {
    private static ConcurrentHashMap<String, List<Integer>> cache = new ConcurrentHashMap<>();

    @Override
    public Optional<Message> validate(Object value, Object model, EnumType annotation) {
        if(value == null && !annotation.required())
            return Optional.empty();
        else if (value instanceof Optional<?>)
            return ((Optional<?>) value).flatMap(v -> validate(v, model, annotation));
        else if (value instanceof Collection<?>) {
            for (Object l : (Collection<?>) value) {
                Optional<Message> m = validate(l, model, annotation);
                if (m.isPresent()) return m;
            }
            return Optional.empty();
        }
        else if(value == null)
            return Optional.empty();
        else if (value instanceof Integer)
            return validateInternal((Integer) value, model, annotation);
        else return Optional.of(new Message("property.enum", "Unknown enum value. Must be an Int"));
    }

    @SuppressWarnings("unchecked")
    private List<Integer> updateCache(EnumType annotation) {
        String key = annotation.clazz().getName();
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else if (annotation.clazz().isEnum()) {
            String methodName = annotation.method();
            List<Integer> items = new ArrayList<>();
            if (methodName != null && !methodName.isEmpty() && Arrays.asList(annotation.clazz().getMethods()).stream().anyMatch(m -> m.getName().equals(methodName))) {
                try {
                    Method method = annotation.clazz().getMethod(methodName);
                    for (Object e : Arrays.asList(annotation.clazz().getEnumConstants())) {
                        items.add((Integer) method.invoke(e));
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } else {
                for (Object e : annotation.clazz().getEnumConstants()) items.add(((Enum<?>) e).ordinal());
            }

            cache.putIfAbsent(key, items);
            return items;
        } else {
            throw new RuntimeException("EnumType annotation must point to a valid Enumeration.");
        }
    }


    private Optional<Message> validateInternal(int value, Object model, EnumType annotation) {
        if (annotation.clazz() == null) throw new RuntimeException("Class type is missing for enumeration annotation");
        List<Integer> all = updateCache(annotation);


        if (all.isEmpty())
            throw new RuntimeException(String.format("Unable to get the enumerated values for: %s", annotation.clazz().getName()));
        return all.contains(value) ? Optional.empty() : Optional.of(new Message("property.enum", "Not a valid value for an enumerated field"));
    }


}
