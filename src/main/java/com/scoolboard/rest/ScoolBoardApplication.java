package com.scoolboard.rest;

import com.scoolboard.rest.config.SbConfig;
import com.scoolboard.rest.filter.CORSResponseFilter;
import com.scoolboard.rest.filter.LoggingResponseFilter;
import com.scoolboard.rest.monitor.AppEventListner;
//import com.wordnik.swagger.config.ConfigFactory;
//import com.wordnik.swagger.config.ScannerFactory;
//import com.wordnik.swagger.config.SwaggerConfig;
//import com.wordnik.swagger.jaxrs.config.ReflectiveJaxrsScanner;
//import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
//import com.wordnik.swagger.jaxrs.listing.ApiListingResource;
//import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
//import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
//import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
//import com.wordnik.swagger.reader.ClassReaders;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.ApplicationPath;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prtis on 9/10/2015.
 */
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
//        register(ApiDeclarationProvider.class);
//        register(ApiListingResourceJSON.class);
//        register(ResourceListingProvider.class);


        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        properties.put(ServerProperties.BV_FEATURE_DISABLE, false);
        this.addProperties(properties);
    }

    @PostConstruct
    public void init() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/scoolboard-api");
        beanConfig.setResourcePackage("com.scoolboard.rest.service");
        beanConfig.setScan(true);
//        final ReflectiveJaxrsScanner scanner = new ReflectiveJaxrsScanner();
//        scanner.setResourcePackage("com.scoolboard.rest");
//
//        ScannerFactory.setScanner(scanner);
//        ClassReaders.setReader(new DefaultJaxrsApiReader());
//
//        final SwaggerConfig config = ConfigFactory.config();
//        config.setApiVersion("1.0");
//        config.setBasePath("http://localhost:8080/scoolboard-api");
    }
}
