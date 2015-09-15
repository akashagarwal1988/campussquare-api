package com.scoolboard.rest.repository.user;

import com.scoolboard.rest.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Created by prtis on 9/14/2015.
 */
public interface UserRepository extends CrudRepository<User, String> {
}
