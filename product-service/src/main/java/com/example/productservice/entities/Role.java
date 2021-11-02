package com.example.productservice.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Role {
    private Long id;
    private String name;

    public static final String GUEST = "GUEST";
    public static final String ADMIN = "ADMIN";
}
