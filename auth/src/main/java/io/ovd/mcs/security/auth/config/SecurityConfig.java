package io.ovd.mcs.security.auth.config;


import io.ovd.mcs.security.auth.model.CustomUserDetails;
import io.ovd.mcs.security.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Created by sergey.ovdienko on 18.07.2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    private UserDetailsService userDetailsService(final UserService userService) {
        return (username -> {return  new CustomUserDetails(userService.findByUsername(username));});
    }

    @Autowired // <-- This is crucial otherwise Spring Boot creates its own
    protected void configureGlobalSecurity(AuthenticationManagerBuilder authManagerBuilder,
                                           UserService userService) throws Exception {
        authManagerBuilder.userDetailsService(userDetailsService(userService));
        authManagerBuilder.authenticationProvider(authenticationProvider(userService));
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService(userService));
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
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