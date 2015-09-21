package com.scoolboard.rest.service.common;

import com.fasterxml.uuid.Generators;
import com.scoolboard.rest.common.constant.ServiceOperation;
import com.scoolboard.rest.entity.AbstractDO;
import com.scoolboard.rest.repository.BaseRepository;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * Created by prtis on 9/14/2015.
 */
public abstract class BaseServiceImpl<TDO extends AbstractDO, ID extends Serializable> implements BaseService<TDO, ID> {

    abstract protected BaseRepository<TDO, ID> getRepository();

    @Override
    public TDO get(ID id, ServiceOperation operation) throws Exception {
        return getRepository().findOne(id);
    }

    @Override
    public TDO add(@Valid TDO t, ServiceOperation operation) throws Exception {
        setField(t, operation);
        return getRepository().save(t);
    }

    @Override
    public Iterable<TDO> addBatch(Collection<TDO> tCol, ServiceOperation operation) throws Exception {
        setFields(tCol, operation);
        return getRepository().save(tCol);
    }

    @Override
    public void update(ID id, TDO t, ServiceOperation operation) throws Exception {
        setField(t, operation);
        getRepository().save(t);
    }

    @Override
    public void updateBatch(Collection<TDO> tCol, ServiceOperation operation) throws Exception {
        setFields(tCol, operation);
        getRepository().save(tCol);
    }

    @Override
    public void delete(ID id, ServiceOperation operation) throws Exception {
        getRepository().delete(id);
    }

    private void setField(TDO t, ServiceOperation operation) {

        if (operation.equals(ServiceOperation.ADD)) {
            t.setId(Generators.timeBasedGenerator().generate().toString());
            t.setCreatedAt(new Date());
        } else if (operation.equals(ServiceOperation.UPDATE))
            t.setUpdatedAt(new Date());
    }

    private void setFields(Collection<TDO> tCol, ServiceOperation operation) {
        tCol.stream().forEach(t -> setField(t, operation));
    }
}
