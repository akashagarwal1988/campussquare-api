package com.scoolboard.rest.service.user;

import com.scoolboard.rest.entity.User;
import com.scoolboard.rest.repository.UserRepository;
import com.scoolboard.rest.service.common.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by prtis on 9/14/2015.
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected UserRepository getRepository() {
        return this.userRepository;
    }
}
