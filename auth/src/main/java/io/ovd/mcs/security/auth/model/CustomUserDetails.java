package io.ovd.mcs.security.auth.model;

import io.ovd.mcs.security.auth.util.GlobalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sergey.Ovdienko on 28.07.2016.
 */

public class CustomUserDetails implements UserDetails{


    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = translate(user.getRoles());
    }

    private Collection<? extends GrantedAuthority> translate(List<Role> roles){
        List<GrantedAuthority> authorities =
            roles
                .stream()
                .map(Role::getName)
                .map(String::toUpperCase)
                .map(name -> {return (name.startsWith(GlobalUtils.ROLE_PREFIX)) ? name : GlobalUtils.ROLE_PREFIX+name;})
                .map(name -> new SimpleGrantedAuthority(name))
                .collect(Collectors.toList());

        return authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
