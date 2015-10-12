package com.scoolboard.rest.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(clazz = OptionsValidator.class)
public @interface Options {
    String message() default "";

    boolean required() default false;

    String[] values();
}