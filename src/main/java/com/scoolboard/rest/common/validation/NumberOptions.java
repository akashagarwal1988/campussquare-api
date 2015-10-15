package com.scoolboard.rest.common.validation;

import lombok.Getter;

import java.util.AbstractMap;
import java.util.Optional;

/**
 * Created by akasha on 2/3/15.
 */
public class NumberOptions {
    @Getter
    private Optional<String> message;

    @Getter
    private Optional<Boolean> required;

    @Getter
    private Optional<AbstractMap.SimpleEntry<Double, RangeBoundary>> min;

    @Getter
    private Optional<AbstractMap.SimpleEntry<Double, RangeBoundary>> max;

    public NumberOptions(Optional<String> message, Optional<Boolean> required, Optional<AbstractMap.SimpleEntry<Double, RangeBoundary>> min, Optional<AbstractMap.SimpleEntry<Double, RangeBoundary>> max) {
        this.message = message;
        this.required = required;
        this.min = min;
        this.max = max;
    }
}
