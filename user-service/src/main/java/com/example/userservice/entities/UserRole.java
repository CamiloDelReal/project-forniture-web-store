package com.example.userservice.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @Embeddable
    @Getter
    @Setter
    public static final class UserRoleId implements Serializable {
        @Column(name = "user_id")
        private Long userId;
        @Column(name = "role_id")
        private Long roleId;
    }
}
