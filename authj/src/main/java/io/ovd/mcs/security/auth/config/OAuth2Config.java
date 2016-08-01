package io.ovd.mcs.security.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;

/**
 * Created by Sergey.Ovdienko on 30.07.2016.
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Value("${config.oauth2.privateKey}")
    private String privateKey;

    @Value("${config.oauth2.publicKey}")
    private String publicKey;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer(){
        JwtAccessTokenConverter tokenEnhancer = new JwtAccessTokenConverter();
        tokenEnhancer.setSigningKey(privateKey);
        tokenEnhancer.setVerifierKey(publicKey);
        return tokenEnhancer;
    }

    /* @Bean
    public JdbcTokenStore tokenStore() {return new JdbcTokenStore(dataSource);}*/
    @Bean
    public JwtTokenStore tokenStore() {
       return new JwtTokenStore(tokenEnhancer());
   }

    /*@Bean
    public JdbcAuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }*/

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
            .passwordEncoder(passwordEncoder)
            .tokenKeyAccess("isAnonymous() || hasRole('ROLE_TRUSTED_CLIENT')") // permitAll()
            .checkTokenAccess("hasRole('ROLE_TRUSTED_CLIENT')"); // isAuthenticated()
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /*endpoints.authorizationCodeServices(authorizationCodeServices())
            .authenticationManager(authManager).tokenStore(tokenStore())
            .approvalStoreDisabled();*/
        // @formatter:off
        endpoints
            // Which authenticationManager should be used for the password grant
            // If not provided, ResourceOwnerPasswordTokenGranter is not configured
            .authenticationManager(authManager)
            // Use JwtTokenStore and our jwtAccessTokenConverter
            .tokenStore(tokenStore())
            .accessTokenConverter(tokenEnhancer());
        // @formatter:on
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.jdbc(dataSource)
            .passwordEncoder(passwordEncoder)
           /* .withClient("my-trusted-client")
            .secret("secret1")
            .authorizedGrantTypes("password", "authorization_code","refresh_token", "implicit")
            .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
            .scopes("read", "write", "trust")
            .resourceIds("oauth2-resource")
            .accessTokenValiditySeconds(60)
            .and()
            .withClient("my-client-with-registered-redirect")
            .secret("secret2")
            .authorizedGrantTypes("authorization_code")
            .authorities("ROLE_CLIENT").scopes("read", "trust")
            .resourceIds("oauth2-resource")
            .redirectUris("http://anywhere?key=value")*/
            /*.and()
            .withClient("my-client-with-secret")
            .authorizedGrantTypes("client_credentials", "password")
            .authorities("ROLE_CLIENT").scopes("read")
            .resourceIds("oauth2-resource").secret("secret")*/
            ;
        // @formatter:on
    }
}
