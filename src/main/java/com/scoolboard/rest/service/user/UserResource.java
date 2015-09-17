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
}
