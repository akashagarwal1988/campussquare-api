package com.scoolboard.rest.entity;

import com.scoolboard.rest.common.data.HasId;

/**
 * Created by prtis on 9/15/2015.
 */
public interface AbstractDO<ID> extends HasId<ID> {
    public ID getId();
    public void setId(ID id);
}
