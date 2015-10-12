package com.scoolboard.rest.common.validation;

import java.util.List;

/**
 * Created by akasha on 2/2/15.
 */
public interface Validation {
    public <T> List<ValidationMessage> validate(T model) throws Exception;
}
