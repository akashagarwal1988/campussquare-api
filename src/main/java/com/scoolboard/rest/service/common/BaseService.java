package com.scoolboard.rest.service.common;

import com.scoolboard.rest.common.constant.ServiceOperation;
import com.scoolboard.rest.common.data.HasId;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by prtis on 9/14/2015.
 */
public interface BaseService<TDO extends HasId, ID extends Serializable> {

    public TDO get(ID id, ServiceOperation operation) throws Exception;

    public Iterable<TDO> getAll() throws Exception;

    public TDO add(@Valid TDO t, ServiceOperation operation) throws Exception;

    public Iterable<TDO> addBatch(Collection<TDO> entities, ServiceOperation operation) throws Exception;

    public void update(ID id, @Valid TDO t, ServiceOperation operation) throws Exception;

    public void updateBatch(Collection<TDO> entities, ServiceOperation operation) throws Exception;

    public void delete(ID id, ServiceOperation operation) throws Exception;
}
