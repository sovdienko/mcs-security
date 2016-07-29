package io.ovd.mcs.security.auth.controller;

import io.ovd.mcs.security.auth.model.Role;
import io.ovd.mcs.security.auth.model.User;
import io.ovd.mcs.security.auth.repository.RoleRepository;
import io.ovd.mcs.security.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by Sergey.Ovdienko on 29.07.2016.
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @RequestMapping("user/{username}")
    public User getUser(@PathVariable("username") String username){
        return userRepository.findByUsername(username);
    }

    @RequestMapping("/user")
    public List<User> getUserList(){
        return userRepository.findAll();
    }

    @RequestMapping("/role")
    public List<Role> getRoleList(){
        return roleRepository.findAll();
    }

    @RequestMapping("/auth")
    public Principal getAuth(Principal principal){
        return principal;
    }
}
