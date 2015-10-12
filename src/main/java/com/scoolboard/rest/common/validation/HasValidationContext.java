package com.scoolboard.rest.common.validation;

/**
 * Created by akasha on 1/30/15.
 */
public interface HasValidationContext<T> {
    Object getValidationContext(T model) throws Exception;
}
