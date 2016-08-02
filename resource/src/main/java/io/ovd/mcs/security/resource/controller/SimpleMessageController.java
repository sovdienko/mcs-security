package io.ovd.mcs.security.resource.controller;

import io.ovd.mcs.security.resource.model.SimpleMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


/**
 * Created by sergey.ovdienko on 18.07.2016.
 */

@RestController
@PreAuthorize("hasRole('USER')")
public class SimpleMessageController {

    @RequestMapping("/message")
    public SimpleMessage home(Principal principal) {

        return new SimpleMessage(String.format("Hello, %s",principal));
    }


    @RequestMapping("/")
    public String auth(Principal principal) {

        return principal.getName();
    }
}
