package com.scoolboard.rest.common.validation;

import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.Optional;

/**
 * Created by akasha on 2/3/15.
 */
@Component
public class MaxValidator extends NumberValidator<Max> {
    @Override
    public String getKey() {
        return "property.max";
    }

    @Override
    protected NumberOptions getOptions(Max annotation) {
        return new NumberOptions(Optional.of(annotation.message()),
                Optional.of(annotation.required()),
                Optional.empty(),
                Optional.of(new AbstractMap.SimpleEntry<>(annotation.value(), annotation.exclusive() ? RangeBoundary.Exclusive : RangeBoundary.Inclusive)));
    }

    @Override
    public Optional<Message> validate(Object value, Object model, Max annotation) {
        if(value == null && !annotation.required())
            return Optional.empty();
        return super.validate(value, model, annotation);
    }
}
