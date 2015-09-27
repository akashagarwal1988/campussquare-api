package com.scoolboard.rest.config;

import com.wordnik.swagger.jaxrs.config.BeanConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.mapping.event.ValidatingCouchbaseEventListener;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

/**
 * Created by prtis on 9/14/2015.
 */
@Slf4j
@Configuration
@ImportResource("classpath:spring/applicationContext.xml")
@EnableCouchbaseRepositories(basePackages = {"com.scoolboard.rest.repository"})
public class SbConfig extends AbstractCouchbaseConfiguration {

    @Value("${swagger.apiVersion}")
    private String apiVersion;

    @Value("${swagger.basePath}")
    private String basePath;

    @Value("${swagger.resourcePackage}")
    private String resourcePackage;

    @Override
    protected List<String> getBootstrapHosts() {
        return Collections.singletonList("107.178.213.209");
    }

    @Override
    protected String getBucketName() {
        return "scoolboard";
    }

    protected String getBucketPassword() {
        return "scoolboard";
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ValidatingCouchbaseEventListener validationEventListener() {
        return new ValidatingCouchbaseEventListener(validator());
    }

    @PostConstruct
    public void init() {
        log.info("api version::" + apiVersion);
        log.info("basePath::" + basePath);
        log.info("resourcePackage::" + resourcePackage);
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(apiVersion);
        beanConfig.setBasePath(basePath);
        beanConfig.setResourcePackage(resourcePackage);
        beanConfig.setScan(true);

    }

}
