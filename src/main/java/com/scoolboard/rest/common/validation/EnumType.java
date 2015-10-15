package com.scoolboard.rest.common.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by akasha on 2/4/15.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(clazz = EnumValidator.class)
public @interface EnumType {
    Class clazz();
    String method() default "getId";
    boolean required() default false;
}