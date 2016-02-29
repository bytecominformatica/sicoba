package br.com.clairtonluz.sicoba.api.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by clairtonluz on 28/02/16.
 */
@RestController()
public class LoginAPI {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
