package com.scoolboard.rest.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scoolboard.rest.monitor.SbRequestEventListner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class LoggingResponseFilter implements ContainerResponseFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingResponseFilter.class);

    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        String method = requestContext.getMethod();

        logger.debug("Requesting " + method + " for path " + requestContext.getUriInfo().getPath());
        responseContext.getHeaders().add("Response-Time", System.currentTimeMillis() - SbRequestEventListner.startTime);
        Object entity = responseContext.getEntity();
        if (entity != null) {
            logger.debug("Response " + new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entity));
        }

    }

}

