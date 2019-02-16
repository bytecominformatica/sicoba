package br.com.clairtonluz.sicoba.config.security;

import br.com.clairtonluz.sicoba.model.entity.security.User;
import br.com.clairtonluz.sicoba.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String BASIC_AUTH_PREFIX = "Basic";
    private ObjectMapper objectMapper = new ObjectMapper();
    private String secret;

    JWTLoginFilter(String url, HttpMethod method, AuthenticationManager authManager, String secret) {
        super(new AntPathRequestMatcher(url, method.name()));
        this.secret = secret;
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        User credentials = extractCredentials(token).orElseThrow(() -> new AccessDeniedException("Acesso negado"));

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    private Optional<User> extractCredentials(String tokenAuth) {
        User user = null;
        if (StringUtil.isNotBlank(tokenAuth) && tokenAuth.startsWith(BASIC_AUTH_PREFIX)) {
            String token = tokenAuth.substring(BASIC_AUTH_PREFIX.length()).trim();
            String[] split = new String(Base64.getDecoder().decode(token)).split(":", 2);
            if (split.length == 2) user = new User(split[0], split[1]);
        }
        return Optional.ofNullable(user);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication auth) {

        TokenAuthenticationService.addAuthentication(response, auth.getName(), secret);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", Instant.now().getEpochSecond());
        data.put("message", "Credenciais inválida");

        response.getOutputStream().println(objectMapper.writeValueAsString(data));
    }
}