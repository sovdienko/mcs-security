package io.ovd.mcs.security.resource.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.apache.log4j.Logger;

/**
 * Created by sergey.ovdienko on 18.07.2016.
 */

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Oauth2Config extends ResourceServerConfigurerAdapter{

    private static final Logger traceUserLogger = Logger.getLogger("traceUserToken");


    // @Value("singin-key:weri9345jdgier9ggndfgjd9g89dfdg")
   // private String singingKey;

    @Value("${config.oauth2.publicKey}")
    private String publicKey;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        final JwtAccessTokenConverter tokenEnhancer = new JwtAccessTokenConverter();
        //tokenEnhancer.setSigningKey(singingKey);
        tokenEnhancer.setVerifierKey(publicKey);
        return tokenEnhancer;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }


    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        traceUserLogger.info(String.format("defaultTokenServices(): %s","777"));

        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        defaultTokenServices.setTokenStore(tokenStore());
        //defaultTokenServices.setSupportRefreshToken();
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(true);
        return defaultTokenServices;

        /*    For example if you want checking the tokens remotely
        //
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(appId);
        tokenServices.setClientSecret(appSecret);
        tokenServices.setCheckTokenEndpointUrl("http://" + authServerHost + ":" + authServerPort + "/oauth/check_token");
        return tokenServices;
        */
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        final OAuth2AuthenticationManager oAuth2AuthenticationManager = new OAuth2AuthenticationManager();
        oAuth2AuthenticationManager.setTokenServices(defaultTokenServices());
        return oAuth2AuthenticationManager;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        resources.tokenServices(defaultTokenServices()).authenticationManager(
                authenticationManager());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/**").authenticated()
                //.access("hasRole('ROLE_USER')")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}




