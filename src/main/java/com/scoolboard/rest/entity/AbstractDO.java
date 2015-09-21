package com.scoolboard.rest.entity;

import com.couchbase.client.java.repository.annotation.Field;
import com.scoolboard.rest.common.data.HasId;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by prtis on 9/15/2015.
 */
public abstract class AbstractDO<ID> implements HasId<ID> {
    abstract public ID getId();

    abstract public void setId(ID id);

    @Field
    @Getter
    @Setter
    private Date createdAt;

    @Field
    @Getter
    @Setter
    private Date updatedAt;
}
