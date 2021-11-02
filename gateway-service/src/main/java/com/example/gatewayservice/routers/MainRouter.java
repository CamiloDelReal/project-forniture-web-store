package com.example.gatewayservice.routers;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class MainRouter {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(r -> r
                        .path("/users/login")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://userservice")
                )
                .route(r -> r
                        .path("/users/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://userservice")
                )
                .route(r -> r
                        .path("/categories/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://productservice")
                )
                .route(r -> r
                        .path("/products/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f
                                .rewritePath("/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://productservice")
                )
                .build();
    }

}
