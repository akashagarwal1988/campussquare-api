package com.scoolboard.rest.repository.user;

import com.couchbase.client.protocol.views.Query;
import com.scoolboard.rest.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by prtis on 9/14/2015.
 */
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    public User findByUserEmail(Query email);
}
