package br.com.clairtonluz.sicoba.config.security;

import br.com.clairtonluz.sicoba.model.entity.security.User;
import br.com.clairtonluz.sicoba.model.entity.security.UserRole;
import br.com.clairtonluz.sicoba.repository.security.UserRepository;
import br.com.clairtonluz.sicoba.repository.security.UserRoleRepository;
import br.com.clairtonluz.sicoba.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String BASIC_AUTH_PREFIX = "Basic";
    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private ObjectMapper objectMapper = new ObjectMapper();
    private String secret;

    JWTLoginFilter(String url, HttpMethod method, AuthenticationManager authManager, String secret, TokenAuthenticationService tokenAuthenticationService, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        super(new AntPathRequestMatcher(url, method.name()));
        this.secret = secret;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        AccountCredentials credentials = extractCredentials(token).orElse(new AccountCredentials());

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    private Optional<AccountCredentials> extractCredentials(String tokenAuth) {
        AccountCredentials user = null;
        if (StringUtils.isNotBlank(tokenAuth) && tokenAuth.startsWith(BASIC_AUTH_PREFIX)) {
            String token = tokenAuth.substring(BASIC_AUTH_PREFIX.length()).trim();
            String[] split = new String(Base64.getDecoder().decode(token)).split(":", 2);
            if (split.length == 2) user = new AccountCredentials(split[0], split[1]);
        }
        return Optional.ofNullable(user);
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication auth) throws IOException {

        tokenAuthenticationService.addAuthentication(response, auth.getName(), secret);
        String username = ((UserDetails) auth.getPrincipal()).getUsername();
        User user = userRepository.findByUsername(username);

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("name", user.getName());
        data.put("username", user.getUsername());
        data.put("timestamp", Instant.now().getEpochSecond());
        data.put("roles", userRoleRepository.findByUser_username(username)
                .stream()
                .map(UserRole::getRole)
                .collect(Collectors.toList())
        );

        response.getOutputStream().println(objectMapper.writeValueAsString(data));
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