package com.scoolboard.rest.common.validation;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by akasha on 2/4/15.
 */
@Component
public class RequiredValidator extends Validator<Object, Object, Required> {
    @Override
    public Optional<Message> validate(Object value, Object model, Required annotation) {
        return validateRequired(value, annotation.required(), annotation.message());
    }
}
