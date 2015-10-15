package com.scoolboard.rest.common.validation;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by akasha on 2/3/15.
 */
public abstract class NumberValidator<A extends Annotation> extends Validator<Object, Object, A> {
    public abstract String getKey();

    protected abstract NumberOptions getOptions(A annotation);

    @Override
    public Optional<Message> validate(Object value, Object model, A annotation) {
        NumberOptions options = getOptions(annotation);

        Function<String, Optional<Message>> message = (msg) -> Optional.of(new Message(getKey(), getMessage(msg, options.getMessage())));

        if (value == null) {
            if (options.getRequired().orElse(false)) {
                return message.apply("This field is required.");
            }
        }

        if (value instanceof Optional<?>) {
            Optional<?> option = (Optional<?>) value;
            if (option.isPresent() && option.get() != null) value = option.get();
            else if (options.getRequired().orElse(false))
                return message.apply("This field is required.");
            else return Optional.empty();
        }

        Map<Class<?>, Function<Object, Optional<String>>> map = new HashMap<>();
        map.put(String.class, (obj) -> {
            String str = (String) obj;
            if (str == null || str.isEmpty())
                return (options.getRequired().orElse(false) ? Optional.of("This field is required and cannot be empty string.") : Optional.empty());
            else return validateNumber((str.length() + 0D), options, "field length");
        });

        map.put(Integer.class, (obj) -> validateNumber(((Integer) obj).doubleValue(), options, "field"));
        map.put(Double.class, (obj) -> validateNumber((Double) obj, options, "field"));
        map.put(Float.class, (obj) -> validateNumber(((Float) obj).doubleValue(), options, "field"));
        map.put(Long.class, (obj) -> validateNumber(((Long) obj).doubleValue(), options, "field"));
        map.put(Collection.class, (obj) -> validateNumber(((Collection) obj).size() + 0D, options, "field"));


        if (map.containsKey(value.getClass()))
            return map.get(value.getClass()).apply(value).flatMap(message);
        else
            throw new RuntimeException(String.format("Unsupported validatable value of class %s", value.getClass().getName()));

    }

    private Optional<String> validateNumber(Double v, NumberOptions options, String fieldName) {
        Optional<String> message = options.getMax().flatMap(max -> {
            if (max.getValue() == RangeBoundary.Inclusive && v > max.getKey())
                return formatMessage("less than or equal to", max.getKey(), fieldName, options);
            else if (max.getValue() == RangeBoundary.Exclusive && v >= max.getKey())
                return formatMessage("less than", max.getKey(), fieldName, options);
            else return Optional.empty();
        });

        if (!message.isPresent()) {
            message = options.getMin().flatMap(min -> {
                if (min.getValue() == RangeBoundary.Inclusive && v < min.getKey())
                    return formatMessage("greater than or equal to", min.getKey(), fieldName, options);
                else if (min.getValue() == RangeBoundary.Exclusive && v >= min.getKey())
                    return formatMessage("greater than", min.getKey(), fieldName, options);
                else return Optional.empty();
            });
        }

        return message;
    }

    private Optional<String> formatMessage(String requirement, Double value, String fieldName, NumberOptions options) {
        return Optional.of(getMessage(String.format("This %s should be %s %s", fieldName, requirement, formatNumber(value)), options.getMessage()));
    }

    protected String formatNumber(Double number) {
        return String.format("%.0f", number);
    }
}
