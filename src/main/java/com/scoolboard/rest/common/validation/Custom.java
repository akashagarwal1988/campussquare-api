package com.scoolboard.rest.common.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ValidatorClass(clazz = CustomValidator.class)
public @interface Custom {
    Class clazz();
}
