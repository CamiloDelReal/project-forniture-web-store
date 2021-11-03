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
                        .path("/store/users/login")
                        .and()
                        .method(HttpMethod.POST)
                        .filters(f -> f
                                .rewritePath("/store/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://userservice")
                )
                .route(r -> r
                        .path("/store/users/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f
                                .rewritePath("/store/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://userservice")
                )
                .route(r -> r
                        .path("/store/categories/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f
                                .rewritePath("/store/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://productservice")
                )
                .route(r -> r
                        .path("/store/products/**")
                        .and()
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                        .filters(f -> f
                                .rewritePath("/store/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://productservice")
                )
                .route(r -> r
                        .path("/store/pictures/*")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f
                                .rewritePath("/store/(?<segment>.*)", "/${segment}")
                        )
                        .uri("lb://productservice")
                )
                .build();
    }

}
