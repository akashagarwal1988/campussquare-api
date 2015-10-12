package com.scoolboard.rest.common.validation;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by akasha on 2/4/15.
 */
public class ClassValidationInfo {
    @Getter
    private final List<ValidatorInfo> classValidators = new ArrayList<>();

    @Getter
    private final HashMap<Field, List<ValidatorInfo>> fieldValidators = new HashMap<>();
}
