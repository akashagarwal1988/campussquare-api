package com.scoolboard.rest.common.validation;

import lombok.Getter;

import java.lang.annotation.Annotation;

/**
 * Created by akasha on 2/4/15.
 */
public class ValidatorInfo {
    @Getter
    private final Annotation annotation;

    @Getter
    private final Validator<?, ? , Annotation> validator;

    public ValidatorInfo(Annotation annotation, Validator<?, ? , Annotation> validator){
        this.annotation = annotation;
        this.validator = validator;
    }
}
