package com.scoolboard.rest;

import com.scoolboard.rest.config.SbConfig;
import com.scoolboard.rest.filter.CORSResponseFilter;
import com.scoolboard.rest.filter.LoggingResponseFilter;
import com.scoolboard.rest.monitor.AppEventListner;
import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResource;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prtis on 9/10/2015.
 */
@Slf4j
@Component
@ApplicationPath("api")
public class ScoolBoardApplication extends ResourceConfig {


    /**
     * Register JAX-RS application components.
     */
    public ScoolBoardApplication() {
        packages("com.scoolboard.rest.service");

        register(SbConfig.class);
        register(AppEventListner.class);

        //register filters
        register(RequestContextFilter.class);
        register(LoggingResponseFilter.class);
        register(CORSResponseFilter.class);

        //register features
        register(JacksonFeature.class);
        register(MultiPartFeature.class);

        //register swagger
        register(ApiListingResource.class);
        register(ApiDeclarationProvider.class);
        register(ApiListingResourceJSON.class);
        register(ResourceListingProvider.class);


        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        properties.put(ServerProperties.BV_FEATURE_DISABLE, false);
        this.addProperties(properties);
    }


}
