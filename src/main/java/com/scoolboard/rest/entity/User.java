package com.scoolboard.rest.entity;

/**
 * Created by prtis on 9/14/2015.
 */

import com.scoolboard.rest.common.validation.Custom;
import com.scoolboard.rest.common.validation.Message;
import com.scoolboard.rest.common.validation.Required;
import com.scoolboard.rest.common.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Document
public class User extends AbstractDO<String> {

    @Id
    @Getter
    @Setter
    private String id;

    @Field
    @Getter
    @Setter
    @Required
    private String firstname;

    @Field
    @Getter
    @Setter
    @Custom(clazz=LastNameValidation.class)
    private String lastname;

    @Version
    private long version;


}

@Component
class LastNameValidation extends Validator<String, User, Custom> {

    @Override
    public Optional<Message> validate(String value, User model, Custom annotation) {
        if(value == null || value.isEmpty())
            return createMessage("Last Name should not be empty");
        else
            return null;    
    }
}
