package com.scoolboard.rest.service.user;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.scoolboard.rest.common.constant.ServiceOperation;
import com.scoolboard.rest.entity.User;
import com.scoolboard.rest.service.common.BaseResource;
import com.scoolboard.rest.service.common.BaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Path("{id}")
    @ApiOperation(value = "Get User", response = User.class)
    @JacksonFeatures(serializationEnable = SerializationFeature.WRAP_ROOT_VALUE,
            deserializationEnable = DeserializationFeature.UNWRAP_ROOT_VALUE)
    public User edit(
            @ApiParam(value = "Id of user", required = true) @PathParam("id") String id) throws Exception {
        return getService().get(id, ServiceOperation.GET);
    }

//    @GET
//    @JacksonFeatures(serializationEnable = SerializationFeature.WRAP_ROOT_VALUE,
//            deserializationEnable = DeserializationFeature.UNWRAP_ROOT_VALUE)
//    public Page<User> findAll() throws Exception {
//        return getService().findAll(new PageRequest(0, 5));
//    }

    @POST
    @JacksonFeatures(serializationEnable = SerializationFeature.WRAP_ROOT_VALUE,
            deserializationEnable = DeserializationFeature.UNWRAP_ROOT_VALUE)
    @ApiOperation(value = "Create User", response = User.class)
    public User create(@ApiParam(name = "User", value = "User Payload", required = true) @Valid User t) throws Exception {
        return getService().add(t, ServiceOperation.ADD);
    }

    @POST
    @Path("/batch")
    @ApiOperation(value = "Create Users", response = User.class, responseContainer = "List")
    public Iterable<User> createBatch(@ApiParam(name = "User", value = "Users Payload", required = true) Collection<User> tCol) throws Exception {
        return getService().addBatch(tCol, ServiceOperation.ADD);
    }

    @PUT
    @Path("{id}")
    @ApiOperation(value = "Update User")
    public void update(@ApiParam(value = "Id of user", required = true) @PathParam("id") String id,
                       @ApiParam(name = "User", value = "User Payload", required = true) @Valid User t) throws Exception {
        getService().update(id, t, ServiceOperation.UPDATE);
    }

    @PUT
    @Path("/batch")
    @ApiOperation(value = "Update Users")
    public void updateBatch(@ApiParam(name = "Users", value = "Users Payload", required = true) Collection<User> tCol) throws Exception {
        getService().updateBatch(tCol, ServiceOperation.UPDATE);
    }

    @DELETE
    @Path("{id}")
    @ApiOperation(value = "Delete Users")
    public void delete(@ApiParam(value = "Id of user", required = true) @PathParam("id") String id) throws Exception {
        getService().delete(id, ServiceOperation.DELETE);
    }
}
