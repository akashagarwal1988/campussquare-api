package com.scoolboard.rest.monitor;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

@Slf4j
public class RequestLogger {

    private static MessageFormat form = new MessageFormat(
            "[YAMP_API] URI = {0} | HTTPMethod = {1} | ClientIP = {2} | ResponseTime = {3} | ResponseCode = {4} | user = {5} | baseURL = {6} | databaseOpTime = {7}");

    public void log(RequestInfo requestInfo) {
        long methodExecution = requestInfo.getEndTime()
                - requestInfo.getStartTime();
        int status = requestInfo.getStatus();

        Object obj = new Object[]{requestInfo.getPath(),
                requestInfo.getMethod(), requestInfo.getIpAddress(),
                methodExecution + "ms", status,
                requestInfo.getLoginId(), requestInfo.getBaseUri(), requestInfo.getDatabaseOpTime() + "ms"};
        if (status >= 500) {
            log.info(form.format(obj) + " | requestBody = " + requestInfo.getProxyEntityStream().toString());
        } else {
            log.info(form.format(obj));
        }
    }
}


