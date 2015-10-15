package com.scoolboard.rest.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(clazz = MaxValidator.class)
public @interface Max {
    String message() default "";

    boolean required() default false;

    double value();

    boolean exclusive() default false;
}