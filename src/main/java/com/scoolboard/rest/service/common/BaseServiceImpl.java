package com.scoolboard.rest.service.common;

import com.scoolboard.rest.common.data.HasId;
import org.springframework.data.repository.CrudRepository;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by prtis on 9/14/2015.
 */
public abstract class BaseServiceImpl<TDO extends HasId, ID extends Serializable> implements BaseService<TDO, ID> {

    abstract protected CrudRepository<TDO, ID> getRepository();

    @Override
    public TDO get(ID id) throws Exception {
        return getRepository().findOne(id);
    }

    @Override
    public TDO add(@Valid TDO t) throws Exception {
        return getRepository().save(t);
    }

    @Override
    public Iterable<TDO> addBatch(Collection<TDO> entities) throws Exception {
        return getRepository().save(entities);
    }

    @Override
    public void update(ID id, TDO t) throws Exception {
        getRepository().save(t);
    }

    @Override
    public void updateBatch(Collection<TDO> entities) throws Exception {
        getRepository().save(entities);
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepository().delete(id);
    }
}
