package io.ovd.mcs.security.client.controller;



import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Created by Sergey.Ovdienko on 20.07.2016.
 */

@RestController
public class ResourceController {

    private static final Logger traceUserLogger = Logger.getLogger("traceUserToken");

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Value("${config.oauth2.resourceURI}")
    private String resourceURI;

    @RequestMapping("/")
    public JsonNode home() {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextImpl securityContext = new SecurityContextImpl();
            securityContext.setAuthentication(new TestingAuthenticationToken("marissa", "koala","ROLE_USER"));
            SecurityContextHolder.setContext(securityContext);
        }
      //  traceUserLogger.info(String.format("c1: %s",restTemplate.getAccessToken().getRefreshToken().getValue()));
        //      traceUserLogger.info(String.format("c2: %s",restTemplate.getAccessToken().getValue()));
        return restTemplate.getForObject(resourceURI, JsonNode.class);

    }

    /*@RequestMapping("/controller")
    public OAuth2RefreshToken token() {
        return restTemplate.getOAuth2ClientContext().getAccessTokenRequest()
    }*/


}
