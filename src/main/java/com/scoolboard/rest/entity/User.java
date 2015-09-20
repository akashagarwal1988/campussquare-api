package com.scoolboard.rest.entity;

/**
 * Created by prtis on 9/14/2015.
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.List;

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
    private String firstname;

    @Field
    @Getter
    @Setter
    private String lastname;

    @Field
    @Getter
    @Setter
    private String email;

    @Field
    @Getter
    @Setter
    private String password;

    @Field
    @Getter
    @Setter
    private List<UserRole> userRole;

    @Field
    @Getter
    @Setter
    private boolean enabled;

    @Version
    private long version;


}

