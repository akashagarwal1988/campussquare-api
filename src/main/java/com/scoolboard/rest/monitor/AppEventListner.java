package com.scoolboard.rest.monitor;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

/**
 * Created by prtis on 9/15/2015.
 */
@Slf4j
public class AppEventListner implements ApplicationEventListener {

    @Override
    public void onEvent(ApplicationEvent applicationEvent) {
        switch (applicationEvent.getType()) {
            case INITIALIZATION_FINISHED:
                log.info("Scool-api application server started.");
                break;
            default:
                // Nothing to log
                break;
        }

    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return new SbRequestEventListner();
    }


}
