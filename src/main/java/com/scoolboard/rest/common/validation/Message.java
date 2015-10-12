package com.scoolboard.rest.common.validation;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by akasha on 2/2/15.
 */
public class Message {

    @Getter
    private String key;

    @Getter
    private List<Object> args;

    public Message(String key, Object... args) {
        this.key = key;
        this.args = Arrays.asList(args);
    }
}
