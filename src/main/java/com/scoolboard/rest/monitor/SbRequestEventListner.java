package com.scoolboard.rest.monitor;

/**
 * Created by prtis on 9/15/2015.
 */

import com.gilt.timeuuid.TimeUuid;
import org.apache.commons.io.input.TeeInputStream;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.slf4j.MDC;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SbRequestEventListner implements RequestEventListener{

    private static RequestLogger requestLogger = new RequestLogger();
    public static long startTime;
    private RequestInfo requestInfo = new RequestInfo();

    private String getHostName() {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9-]+)\\.pbp\\.(\\D\\D\\d).*");
        String hostName = "hostNotFound";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
        }

        Matcher matcher = pattern.matcher(hostName);
        while (matcher.find()) {
            return "" + matcher.group(1).charAt(0) + matcher.group(1).charAt(matcher.group(1).length() - 1) + matcher.group(2).charAt(0);
        }
        return hostName;
    }


    @Override
    public void onEvent(RequestEvent requestEvent) {
        switch (requestEvent.getType()) {
            case MATCHING_START:
                startTime = System.currentTimeMillis();
                requestInfo.setStartTime(System.currentTimeMillis());
                requestInfo.setPath(requestEvent.getUriInfo().getPath());
                requestInfo.setBaseUri(requestEvent.getUriInfo().getRequestUri()
                        .toString());
                requestInfo.setMethod(requestEvent.getContainerRequest()
                        .getMethod());
                ByteArrayOutputStream proxyOutputStream = new ByteArrayOutputStream();

                requestEvent.getContainerRequest().setEntityStream(new TeeInputStream(requestEvent.getContainerRequest().getEntityStream(), proxyOutputStream));

                requestInfo.setProxyEntityStream(proxyOutputStream);
                String uuid = startTime + "-" + getHostName() + "-" + Thread.currentThread().getId() +
                        (requestInfo.getUserId() != null ? "-u" + requestInfo.getUserId() : "") +
                        "-t" + getRequestMethodKey(requestInfo.getMethod());
                requestInfo.setUUID(TimeUuid.apply().timestamp());
                MDC.put("UUID", "ID:" + uuid);
                MDC.put("ID_START", "[");
                MDC.put("ID_END", "]");
                requestEvent.getContainerRequest().setProperty("requestInfo",
                        requestInfo);
                break;
            case FINISHED:
                requestInfo.setEndTime(System.currentTimeMillis());
                if (requestEvent.getContainerResponse() != null) {
                    requestInfo.setStatus(requestEvent.getContainerResponse().getStatus());
                }
                requestLogger.log(requestInfo);
                break;
            default:
                break;

        }
    }

    private Integer getRequestMethodKey(String method) {
        switch (method) {
            case "POST":
                return 1;
            case "GET":
                return 2;
            case "PUT":
                return 3;
            case "DELETE":
                return 4;
            case "OPTIONS":
                return 5;
            default:
                return -1;
        }
    }

}
