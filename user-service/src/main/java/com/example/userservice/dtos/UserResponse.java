package com.example.userservice.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private List<RoleResponse> roles;
}
