package com.example.userservice.seeders;

import com.example.userservice.entities.Role;
import com.example.userservice.entities.User;
import com.example.userservice.repositories.RoleRepository;
import com.example.userservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DatabaseSeeder {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseSeeder(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener
    public void seedDatabase(ContextRefreshedEvent event) {
        if(userRepository.count() == 0 && roleRepository.count() == 0) {
            log.info("Seeding roles");
            Role guestRole = new Role(Role.GUEST);
            guestRole = roleRepository.save(guestRole);
            Role adminRole = new Role(Role.ADMIN);
            adminRole = roleRepository.save(adminRole);

            log.info("Seeding users");
            User guestUser = new User("Guest", "", "guest@mail.com", passwordEncoder.encode("12345"), List.of(guestRole));
            guestUser = userRepository.save(guestUser);
            User adminUser = new User("Admin", "", "admin@mail.com", passwordEncoder.encode("12345"), List.of(adminRole));
            adminUser = userRepository.save(adminUser);
        }
    }
}
