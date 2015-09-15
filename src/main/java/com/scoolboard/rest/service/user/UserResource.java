package com.scoolboard.rest.service.user;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.scoolboard.rest.entity.User;
import com.scoolboard.rest.service.common.BaseResource;
import com.scoolboard.rest.service.common.BaseService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by prtis on 9/14/2015.
 */
@Path("users")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Resource
@Api(value = "/users", description = "Operations for Users")
@Component
public class UserResource extends BaseResource<User, String> {

    @Autowired
    private UserService userService;


    @Override
    public BaseService<User, String> getService() {
        return userService;
    }

    @GET
    @Path("{id}/test")
    @JacksonFeatures(serializationEnable = SerializationFeature.WRAP_ROOT_VALUE,
            deserializationEnable = DeserializationFeature.UNWRAP_ROOT_VALUE)
    public Response edit(@PathParam("id") int id) throws Exception {
        return Response.status(200).build();
    }

    @GET
    @Path("{id}")
    @JacksonFeatures(serializationEnable = SerializationFeature.WRAP_ROOT_VALUE,
            deserializationEnable = DeserializationFeature.UNWRAP_ROOT_VALUE)
    public User edit(@PathParam("id") String id) throws Exception {
        return getService().get(id);
    }

    @POST
    @JacksonFeatures(serializationEnable = SerializationFeature.WRAP_ROOT_VALUE,
            deserializationEnable = DeserializationFeature.UNWRAP_ROOT_VALUE)
    public User create(@Valid User t) throws Exception {
        return getService().add(t);
    }

    @POST
    @Path("/batch")
    public Iterable<User> createBatch(Collection<User> entities) throws Exception {
        return getService().addBatch(entities);
    }

    @PUT
    @Path("{id}")
    public void update(@PathParam("id") String id, @Valid User t) throws Exception {
        getService().update(id, t);
    }

    @PUT
    @Path("/batch")
    public void updateBatch(Collection<User> entities) throws Exception {
        getService().updateBatch(entities);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) throws Exception {
        getService().delete(id);
    }

}
