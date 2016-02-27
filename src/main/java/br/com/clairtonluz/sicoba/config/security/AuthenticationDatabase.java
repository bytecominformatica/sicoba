package br.com.clairtonluz.sicoba.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by clairtonluz on 27/02/16.
 */
public class AuthenticationDatabase implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("####################");
        System.out.println("####################");
        System.out.println("####################");
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getAuthorities());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.isAuthenticated());
        authentication.setAuthenticated(false);
        System.out.println("####################");
        System.out.println("####################");
        System.out.println("####################");
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("####################2");
        System.out.println(authentication.getClass());
        System.out.println("####################");
        return true;
    }
}
