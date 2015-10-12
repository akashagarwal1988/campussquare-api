package com.scoolboard.rest.common.validation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonRootName("validationmessages")
@NoArgsConstructor @AllArgsConstructor
public class ValidationMessage {

    private String message;
    private String key;
    private Optional<String> objectName;
    private Optional<String> propertyName;
    private Optional<Long> id;

    public void setId(Optional<Long> id) {
        this.id = id;
    }
 
    @JsonIgnore
    public Optional<Long> getId() {
        return id;
    }

    @JsonProperty("id")
    public Long getMessageId() {
        return id.orElse(0L);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @JsonIgnore
    public Optional<String> getObjectName() {
        return objectName;
    }

    @JsonProperty("objectName")
    public String getObject() {
        return objectName != null ? objectName.get().substring(0,1).toLowerCase() + objectName.get().substring(1) : null;
    }

    public void setObjectName(Optional<String> objectName) {
        this.objectName = objectName;
    }

    public void setPropertyName(Optional<String> propertyName) {
        this.propertyName = propertyName;
    }

    @JsonIgnore
    public Optional<String> getPropertyName() {
        return propertyName;
    }

    @JsonProperty("propertyName")
    public String getProperty() {
        return propertyName.orElse(null);
    }

    public static ValidationMessage withMessageAndKey(String message, String key){
        ValidationMessage aptVM = new ValidationMessage();
        aptVM.setMessage(message);
        aptVM.setKey(key);
        aptVM.setPropertyName(Optional.of(""));
        aptVM.setId(Optional.of(0l));
        return aptVM;
    }

    public ValidationMessage(String key,String message){
        this.message =message;
        this.key =key;
        this.setPropertyName(Optional.of(""));
        this.setId(Optional.of(0l));

    }
}
