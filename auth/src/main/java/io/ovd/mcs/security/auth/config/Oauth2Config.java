package io.ovd.mcs.security.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@Configuration
@EnableAuthorizationServer
//@RestController
//@EnableResourceServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter
{
    @Value("${config.oauth2.privateKey}")
    private String privateKey;

    @Value("${config.oauth2.publicKey}")
    private String publicKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    //@Value("singin-key:weri9345jdgier9ggndfgjd9g89dfdg")
    //private String singingKey;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer(){
           JwtAccessTokenConverter tokenEnhancer = new JwtAccessTokenConverter();
            //converter.setSigningKey(singingKey);
            tokenEnhancer.setSigningKey(privateKey);
            tokenEnhancer.setVerifierKey(publicKey);
            return tokenEnhancer;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(tokenEnhancer());
    }


    /**
     * Defines the security constraints on the token endpoints /oauth/token_key and /oauth/check_token
     * Client credentials are required to access the endpoints
     *
     * @param oauthServer
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("isAnonymous() || hasRole('ROLE_TRUSTED_CLIENT')") // permitAll()
                .checkTokenAccess("hasRole('ROLE_TRUSTED_CLIENT')"); // isAuthenticated()
    }

    /**
     * Defines the authorization and token endpoints and the token services
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints

                // Which authenticationManager should be used for the password grant
                // If not provided, ResourceOwnerPasswordTokenGranter is not configured
                .authenticationManager(authenticationManager)

                // Use JwtTokenStore and our jwtAccessTokenConverter
                .tokenStore(tokenStore())
                .accessTokenConverter(tokenEnhancer())
        ;
    }

    /*@RequestMapping("/user")
    public Principal user(Principal user) { return user;  }*/


   @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

       clients.inMemory()

               // Confidential client where client secret can be kept safe (e.g. server side)
               .withClient("confidential").secret("secret")
               .authorities("ROLE_TRUSTED_CLIENT")
               .authorizedGrantTypes("client_credentials", "authorization_code", "refresh_token")
               .scopes("read", "write")
               .redirectUris("http://localhost:8080/client/")
               .accessTokenValiditySeconds(30)
               .and()

               // Public client where client secret is vulnerable (e.g. mobile apps, browsers)
               .withClient("public") // No secret!
               .authorizedGrantTypes("client_credentials", "implicit")
               .scopes("read")
               .redirectUris("http://localhost:8080/client/")

               .and()

               // Trusted client: similar to confidential client but also allowed to handle user password
               .withClient("trusted").secret("secret")
               .authorities("ROLE_TRUSTED_CLIENT")
               .authorizedGrantTypes("client_credentials", "password", "authorization_code", "refresh_token")
               .scopes("read", "write")
               //.redirectUris("http://localhost:8080/client/")
               .autoApprove(".*")
               .accessTokenValiditySeconds(3000);

        }
}

