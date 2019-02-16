package br.com.clairtonluz.sicoba.config.security;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.clairtonluz.sicoba.util.StringUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenAuthenticationService {
    private static final long EXPIRATION_TIME = 860_000_000;// EXPIRATION_TIME = 10 dias
//    private static final String SECRET = "MySecret";
    private static final String TOKEN_PREFIX = "Bearer";

    static void addAuthentication(HttpServletResponse response, String username, String secret) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();


        response.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + " " + JWT);
    }

    static Authentication getAuthentication(HttpServletRequest request, String secret) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtil.isNotBlank(token) && token.startsWith(TOKEN_PREFIX)) {
            String username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
            }
        }
        return null;
    }
}
