package io.ovd.mcs.security.auth.service.impl;

import io.ovd.mcs.security.auth.model.Role;
import io.ovd.mcs.security.auth.model.User;
import io.ovd.mcs.security.auth.repository.UserRepository;
import io.ovd.mcs.security.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sergey.Ovdienko on 30.07.2016.
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public void registerUser(String username, String password, List<Role> roles){
        userRepository.save(new User(username, passwordEncoder.encode(password), roles));
    }

    public User findByUsername(String username){
       return  userRepository.findByUsername(username);
    }

    public Long userCount(){
        return userRepository.count();
    }
}
