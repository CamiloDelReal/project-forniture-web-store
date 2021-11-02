package com.example.userservice.security;

import com.example.userservice.entities.User;
import com.example.userservice.services.UserService;
import com.example.userservice.utils.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {
    private final SecurityConfig securityConfig;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public AuthorizationFilter(SecurityConfig securityConfig, ObjectMapper objectMapper, @Lazy UserService userService) {
        this.securityConfig = securityConfig;
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith(securityConfig.getTokenType())) {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            if(authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authentication = null;
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.replace(securityConfig.getTokenType(), "");
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(securityConfig.getTokenKey())
                    .parseClaimsJws(token)
                    .getBody();
            String subject = claims.getSubject();
            User userFromToken = objectMapper.readValue(subject, User.class);
            log.info("User from token " + userFromToken);
            User user = userService.findByEmail(userFromToken.getEmail());
            if (user != null) {
                authentication = new UsernamePasswordAuthenticationToken(user, null,
                        user.getRoles().stream().map(role -> new SimpleGrantedAuthority(String.format("ROLE_%s", role.getName().toUpperCase()))).collect(Collectors.toList()));
            }
        } catch (Exception ex) {
            log.info("Exception captured (token)", ex);
        }
        return authentication;
    }

}
