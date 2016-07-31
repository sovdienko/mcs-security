package io.ovd.mcs.security.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by Sergey.Ovdienko on 31.07.2016.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder authManagerBuilder) throws Exception {

        // @formatter:off
       authManagerBuilder
            .jdbcAuthentication().passwordEncoder(passwordEncoder)
            .dataSource(dataSource)
          /* .withUser("dave").password(passwordEncoder.encode("secret")).roles("USER","TEST")
            .and()
            .withUser("admin").password(passwordEncoder.encode("1")).roles("USER","ADMIN")*/;
        // @formatter:on

    }

  /*  @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()//.permitAll()
            .and()
            .httpBasic().disable()
            .anonymous().disable()
            .authorizeRequests().anyRequest().authenticated()
        ;
    }*/
}
