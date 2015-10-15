package com.scoolboard.rest.common.validation;

import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Optional;

/**
 * Created by akasha on 2/4/15.
 */
@Component
public class MinValidator extends NumberValidator<Min> {
    @Override
    public String getKey() {
        return "property.min";
    }

    @Override
    protected NumberOptions getOptions(Min annotation) {
        return new NumberOptions(Optional.of(annotation.message()),
                Optional.of(annotation.required()),
                Optional.of(new AbstractMap.SimpleEntry<>(annotation.value(), annotation.exclusive() ? RangeBoundary.Exclusive : RangeBoundary.Inclusive)),
                Optional.empty());
    }

    @Override
    public Optional<Message> validate(Object value, Object model, Min annotation) {
        if(value == null && !annotation.required())
            return Optional.empty();
        return super.validate(value, model, annotation);
    }
}
