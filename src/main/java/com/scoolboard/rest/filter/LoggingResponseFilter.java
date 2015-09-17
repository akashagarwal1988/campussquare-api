package com.scoolboard.rest.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoolboard.rest.monitor.SbRequestEventListner;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

@Slf4j
public class LoggingResponseFilter implements ContainerResponseFilter {

    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        String method = requestContext.getMethod();

        log.debug("Requesting " + method + " for path " + requestContext.getUriInfo().getPath());
        responseContext.getHeaders().add("Response-Time", System.currentTimeMillis() - SbRequestEventListner.startTime);
        Object entity = responseContext.getEntity();
        if (entity != null) {
            log.debug("Response " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entity));
        }

    }

}


