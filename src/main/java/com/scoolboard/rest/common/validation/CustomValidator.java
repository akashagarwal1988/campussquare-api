package com.scoolboard.rest.common.validation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class CustomValidator extends Validator<Object, Object, Custom> {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<Message> validate(Object value, Object model, Custom annotation) {
        Validator<?, ?, Custom> validator = (Validator<?, ?, Custom>)beanFactory.getBean(annotation.clazz());
        return validator.validateAny(value, model, annotation, getParents(), getContexts());
    }
}