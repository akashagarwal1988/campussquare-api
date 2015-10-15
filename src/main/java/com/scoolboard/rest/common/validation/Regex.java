package com.scoolboard.rest.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(clazz = RegexValidator.class)
public @interface Regex {
    String message() default "";

    boolean required() default false;

    String[] regex() default {};

    boolean inclusive() default true;

    boolean phone() default false;

    boolean email() default false;

    boolean url() default false;

    boolean urlWithProtocol() default false;
}