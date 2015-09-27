package com.scoolboard.rest.entity;

/**
 * Created by prtis on 9/14/2015.
 */

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.wordnik.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="User", description="User model for the documentation")
@Document
public class User extends AbstractDO<String> {

    @Id
    @Getter
    @Setter
    private String id;

    @Field
    @Getter
    @Setter
    private String firstname;

    @Field
    @Getter
    @Setter
    private String lastname;

    @Version
    private long version;
}
