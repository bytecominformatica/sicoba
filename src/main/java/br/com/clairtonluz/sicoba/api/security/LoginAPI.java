package br.com.clairtonluz.sicoba.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by clairtonluz on 28/02/16.
 */
@RestController()
public class LoginAPI {

    @Autowired
    private Environment environment;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping("/profile")
    public String[] profile() {
        return environment.getActiveProfiles();
    }
}
