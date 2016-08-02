package io.ovd.mcs.security.client.controller;



import com.fasterxml.jackson.databind.JsonNode;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.security.Principal;

/**
 * Created by Sergey.Ovdienko on 20.07.2016.
 */

@RestController
public class ResourceController {

    @Autowired
    AuthenticationManager authenticationManager;

    private static final Logger traceUserLogger = Logger.getLogger("traceUserToken");

    @Autowired
    private OAuth2RestOperations restTemplate;

    @Value("${config.oauth2.resourceURI}")
    private String resourceURI;

    @RequestMapping("/auth")
    public String home() {
        String user = restTemplate.getForObject(resourceURI, String.class);
        return user;

    }

    @RequestMapping("/")
    public Principal auth(Principal principal) {
        return principal;
    }

    @RequestMapping("/message")
    public JsonNode message() {
        return restTemplate.getForObject(resourceURI, JsonNode.class);
    }

}
