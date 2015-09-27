package com.scoolboard.rest.service.common;

import com.scoolboard.rest.common.data.HasId;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by prtis on 9/14/2015.
 */
@Component
public abstract class BaseResource<TDO extends HasId, ID extends Serializable> {

    public abstract BaseService<TDO, ID> getService();
}
