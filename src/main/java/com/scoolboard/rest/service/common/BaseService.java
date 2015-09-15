package com.scoolboard.rest.service.common;

import com.scoolboard.rest.common.data.HasId;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created by prtis on 9/14/2015.
 */
public interface BaseService<TDO extends HasId, ID extends Serializable> {

    public TDO get(ID id) throws Exception;

    public TDO add(@Valid TDO t) throws Exception;

    public  Iterable<TDO> addBatch(Collection<TDO> entities) throws Exception;

    public void update(ID id, @Valid TDO t) throws Exception;

    public void updateBatch(Collection<TDO> entities) throws Exception;

    public void delete(ID id) throws Exception;
}
