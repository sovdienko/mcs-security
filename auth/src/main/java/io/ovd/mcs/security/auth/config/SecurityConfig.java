package io.ovd.mcs.security.auth.config;


import io.ovd.mcs.security.auth.model.CustomUserDetails;
import io.ovd.mcs.security.auth.model.Role;
import io.ovd.mcs.security.auth.model.User;
import io.ovd.mcs.security.auth.repository.RoleRepository;
import io.ovd.mcs.security.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;

/**
 * Created by sergey.ovdienko on 18.07.2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   /* @Override
    @Autowired // <-- This is crucial otherwise Spring Boot creates its own
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password")
                .roles("USER")
                .and()
                .withUser("admin").password("password")
                .roles("USER", "ADMIN");
        }*/

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder,
                                      UserRepository userRepository) throws Exception {


        if (userRepository.count()==0) {
            userRepository.save(new User("user", "pass", Arrays.asList(new Role("USER"))));
            userRepository.save(new User("admin", "admin1", Arrays.asList(new Role("USER"),new Role("ADMIN"))));
            userRepository.save(new User("test", "test1", Arrays.asList(new Role("ROLE_FUN"),new Role("TEST"))));
        }
        builder.userDetailsService(userDetailsService(userRepository));
    }


    private UserDetailsService userDetailsService(final UserRepository userRepository) {
        return (username -> {return  new CustomUserDetails(userRepository.findByUsername(username));});
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()//.permitAll()
                .and()
                .httpBasic().disable()
                .anonymous().disable()
                .authorizeRequests().anyRequest().authenticated()
        ;
    }





}