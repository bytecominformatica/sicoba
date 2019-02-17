package br.com.clairtonluz.sicoba.config.security;

import br.com.clairtonluz.sicoba.repository.security.UserRoleRepository;
import br.com.clairtonluz.sicoba.util.StringUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
class TokenAuthenticationService {
    private static final long EXPIRATION_TIME = 860_000_000;// EXPIRATION_TIME = 10 dias
    private static final String TOKEN_PREFIX = "Bearer";
    private final UserRoleRepository userRoleRepository;

    TokenAuthenticationService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    void addAuthentication(HttpServletResponse response, String username, String secret) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + " " + JWT);
    }

    Authentication getAuthentication(HttpServletRequest request, String secret) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotBlank(token) && token.startsWith(TOKEN_PREFIX)) {
            String username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();

            if (StringUtils.isNotBlank(username)) {
                List<GrantedAuthority> roleList = userRoleRepository.findByUser_username(username)
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRole()))
                        .collect(Collectors.toList());

                return new UsernamePasswordAuthenticationToken(username, null, roleList);
            }
        }
        return null;
    }
}
