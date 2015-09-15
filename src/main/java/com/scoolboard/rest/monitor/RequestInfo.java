package com.scoolboard.rest.monitor;

import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;

public class RequestInfo {

    @Getter
    @Setter
    private String suffixRequestId;

    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    private String method;

    @Getter
    @Setter
    private int status;

    @Getter
    @Setter
    private long startTime;

    @Getter
    @Setter
    private long endTime;

    @Getter
    @Setter
    private ByteArrayOutputStream proxyEntityStream;

    @Getter
    @Setter
    private String baseUri;

    @Getter
    @Setter
    private String ipAddress;

    @Getter
    @Setter
    private String userEmail;

    @Getter
    @Setter
    private Integer userId;

    @Getter
    @Setter
    private Long databaseOpTime;

    @Getter
    @Setter
    private String loginId;

    @Getter
    @Setter
    private Long UUID;
}
