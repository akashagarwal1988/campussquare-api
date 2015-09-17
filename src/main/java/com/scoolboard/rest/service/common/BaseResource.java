package com.scoolboard.rest.service.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.scoolboard.rest.common.constant.ServiceOperation;
import com.scoolboard.rest.common.data.HasId;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by prtis on 9/14/2015.
 */
@Component
public abstract class BaseResource<TDO extends HasId, ID extends Serializable> {

    public abstract BaseService<TDO, ID> getService();

    @GET
    @Path("{id}")
    @JacksonFeatures(serializationEnable = SerializationFeature.WRAP_ROOT_VALUE,
            deserializationEnable = DeserializationFeature.UNWRAP_ROOT_VALUE)
    public TDO edit(@PathParam("id") ID id) throws Exception {
        return getService().get(id, ServiceOperation.GET);
    }

    @POST
    @JacksonFeatures(serializationEnable = SerializationFeature.WRAP_ROOT_VALUE,
            deserializationEnable = DeserializationFeature.UNWRAP_ROOT_VALUE)
    public TDO create(@Valid TDO t) throws Exception {
        return getService().add(t, ServiceOperation.ADD);
    }

    @POST
    @Path("/batch")
    public Iterable<TDO> createBatch(Collection<TDO> tCol) throws Exception {
        return getService().addBatch(tCol, ServiceOperation.ADD);
    }

    @PUT
    @Path("{id}")
    public void update(@PathParam("id") ID id, @Valid TDO t) throws Exception {
        getService().update(id, t, ServiceOperation.UPDATE);
    }

    @PUT
    @Path("/batch")
    public void updateBatch(Collection<TDO> tCol) throws Exception {
        getService().updateBatch(tCol, ServiceOperation.UPDATE);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") ID id) throws Exception {
        getService().delete(id, ServiceOperation.DELETE);
    }
}
