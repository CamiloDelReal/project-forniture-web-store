package com.example.userservice.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class SecurityConfig {
    @Value("${security.claims.separator}")
    private String separator;
    @Value("${security.token.key}")
    private String tokenKey;
    @Value("${security.token.type}")
    private String tokenType;
    @Value("${security.token.validity}")
    private long validity;
}
