package com.scoolboard.rest.service.user;

import com.scoolboard.rest.entity.User;
import com.scoolboard.rest.repository.user.UserRepository;
import com.scoolboard.rest.service.common.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Created by prtis on 9/14/2015.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    protected CrudRepository<User, String> getRepository() {
        return userRepository;
    }
}
