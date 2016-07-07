package com.scoolboard.rest.service.authentication;

import com.couchbase.client.protocol.views.ComplexKey;
import com.couchbase.client.protocol.views.Query;
import com.scoolboard.rest.entity.UserRole;
import com.scoolboard.rest.repository.user.UserRepository;
import com.scoolboard.rest.service.common.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by akasha on 9/18/15.
 */
@Slf4j
@Service("userDetailsService")
public class MyUserDetailService extends BaseServiceImpl<com.scoolboard.rest.entity.User, String> implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    protected UserRepository getRepository() {
        return userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Query query = new Query();
        query.setKey(ComplexKey.of(username));

        com.scoolboard.rest.entity.User user = getRepository().findByUserEmail(query);
        List<GrantedAuthority> authorities = buildUserAuthority(new HashSet<UserRole>(user.getUserRole()));

        return buildUserForAuthentication(user, authorities);
    }

    // Converts com.scoolboard.rest.entity.User user to
    // org.springframework.security.core.userdetails.User
    private User buildUserForAuthentication(com.scoolboard.rest.entity.User user,
                                            List<GrantedAuthority> authorities) {
        return new User(user.getEmail(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }

        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

        return result;
    }

    // for testing and user creation purpose
    private String getHashedPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;

    }
}
