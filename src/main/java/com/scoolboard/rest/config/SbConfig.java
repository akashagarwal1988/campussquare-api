package com.scoolboard.rest.config;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.core.mapping.event.ValidatingCouchbaseEventListener;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;
import java.util.List;

/**
 * Created by prtis on 9/14/2015.
 */
@Configuration
@ImportResource("classpath:spring/applicationContext.xml")
@EnableCouchbaseRepositories("com.scoolboard.rest.repository")
public class SbConfig extends AbstractCouchbaseConfiguration {

    @Override
    protected List<String> bootstrapHosts() {
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

    private final CouchbaseConnectionFactoryBuilder builder = new CouchbaseConnectionFactoryBuilder();
    private CouchbaseConnectionFactory connectionFactory;

    @Override
    @Bean(destroyMethod = "shutdown")
    public CouchbaseClient couchbaseClient() throws Exception {
        setLoggerProperty(couchbaseLogger());

        if(connectionFactory == null){
            connectionFactory = builder.buildCouchbaseConnection(
                    bootstrapUris(bootstrapHosts()),
                    getBucketName(),
                    getBucketPassword()
            );
        }

        return new CouchbaseClient(connectionFactory);
    }

}
