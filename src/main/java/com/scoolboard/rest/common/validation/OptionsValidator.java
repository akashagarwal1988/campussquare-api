package com.scoolboard.rest.common.validation;

import com.google.common.base.Joiner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by akasha on 2/3/15.
 */
@Component
public class OptionsValidator extends Validator<Object, Object, Options> {
    @Override
    public Optional<Message> validate(Object value, Object model, Options annotation) {

        Function<String, Optional<Message>> message = (msg) -> Optional.of(new Message("property.options", getMessage(msg, Optional.of(annotation.message()))));

        if (value instanceof Optional<?>) {
            Optional<?> option = (Optional<?>) value;
            if (option.isPresent() && option.get() != null) value = option.get();
            else if (annotation.required())
                return message.apply("This field is required.");
            else return Optional.empty();
        }

        List<String> values = Arrays.asList(annotation.values());
        if (!values.contains(value.toString()))
            return message.apply(getMessage(String.format("This field should be one of the following options : %s.", Joiner.on(",").join(values)), Optional.of(annotation.message())));
        else return Optional.empty();
    }
}
