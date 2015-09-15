package com.scoolboard.rest.service.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import com.scoolboard.rest.common.data.HasId;
import com.scoolboard.rest.service.common.BaseService;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created by prtis on 9/14/2015.
 */
@Component
public abstract class BaseResource<TDO extends HasId, ID extends Serializable> {

    public abstract BaseService<TDO, ID> getService();
}
