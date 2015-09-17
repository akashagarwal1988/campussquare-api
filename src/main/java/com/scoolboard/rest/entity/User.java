package com.scoolboard.rest.entity;

/**
 * Created by prtis on 9/14/2015.
 */

import com.scoolboard.rest.common.data.HasId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

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

    @Version
    private long version;
}
