package com.scoolboard.rest.repository;

import com.scoolboard.rest.entity.AbstractDO;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;

/**
 * Created by prtis on 9/20/2015.
 */
public interface BaseRepository<TDO extends AbstractDO, ID extends Serializable> extends CrudRepository<TDO, ID> {}
