package io.ovd.mcs.security.auth.service;

import io.ovd.mcs.security.auth.model.Role;
import io.ovd.mcs.security.auth.model.User;

import java.util.List;

/**
 * Created by Sergey.Ovdienko on 30.07.2016.
 */

public interface UserService {

    public void registerUser(String username, String password, List<Role> roles);

    public User findByUsername(String username);

    public Long userCount();

}


