package com.example.userservice.services;

import com.example.userservice.dtos.LoginRequest;
import com.example.userservice.dtos.LoginResponse;
import com.example.userservice.dtos.UserRequest;
import com.example.userservice.dtos.UserResponse;
import com.example.userservice.entities.Role;
import com.example.userservice.entities.User;
import com.example.userservice.repositories.RoleRepository;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.repositories.UserRoleRepository;
import com.example.userservice.utils.SecurityConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final SecurityConfig securityConfig;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager,
                       BCryptPasswordEncoder passwordEncoder, ModelMapper modelMapper, ObjectMapper objectMapper,
                       SecurityConfig securityConfig, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.securityConfig = securityConfig;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUser " + username);
        UserDetails userDetails = null;
        User user = userRepository.findByEmail(username).orElse(null);
        if (user != null) {
            userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    true, true, true, true,
                    user.getRoles().stream().map(role -> new SimpleGrantedAuthority(String.format("ROLE_%s", role.getName().toUpperCase()))).collect(Collectors.toList()));
        }
        return userDetails;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email).orElse(null) == null;
    }

    public boolean hasRoleAdmin(UserRequest userRequest) {
        return userRequest.getRoles().stream().anyMatch(role -> role.equalsIgnoreCase(Role.ADMIN));
    }

    public boolean hasRoleAdmin(User user) {
        return user.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(Role.ADMIN));
    }

    public LoginResponse login(LoginRequest loginRequest) throws JsonProcessingException {
        log.info("login " + loginRequest);
        LoginResponse response = null;
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(credentials);
        } catch (Exception ex) {
            log.error("Exception captured", ex);
        }
        if (authentication != null) {
            User user = userRepository.findByEmail(loginRequest.getEmail()).orElse(null);
            log.info("User " + user);
            if (user != null) {
                String subject = objectMapper.writeValueAsString(user);
                long currentTime = System.currentTimeMillis();
                Date expiration = new Date(currentTime + securityConfig.getValidity());
                String token = Jwts.builder()
                        .setSubject(subject)
                        .setIssuedAt(new Date(currentTime))
                        .setExpiration(expiration)
                        .signWith(SignatureAlgorithm.HS512, securityConfig.getTokenKey())
                        .compact();
                response = new LoginResponse(securityConfig.getTokenType(), token, expiration);
            }
        }
        log.info("login returning " + response);
        return response;
    }

    public List<UserResponse> getAll() {
        log.info("getAll");
        List<User> users = userRepository.findAll();
        List<UserResponse> response = null;
        if(users != null && !users.isEmpty()) {
            response = users.stream().map(user -> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
        } else {
            response = new ArrayList<>();
        }
        log.info("getAll returning " + response);
        return response;
    }

    public UserResponse getById(Long id) {
        log.info("getById " + id);
        User user = userRepository.findById(id).orElse(null);
        UserResponse response = null;
        if(user != null) {
            response = modelMapper.map(user, UserResponse.class);
        }
        log.info("getById returning " + response);
        return response;
    }

    public UserResponse create(UserRequest userRequest) {
        log.info("create " + userRequest);
        User user = modelMapper.map(userRequest, User.class);
        List<Role> roles = null;
        if(userRequest.getRoles() != null) {
            roles = userRequest.getRoles().stream().map(role -> roleRepository.findByName(role).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
        } else {
            Role guestRole = roleRepository.findByName(Role.GUEST).orElse(null);
            roles = List.of(guestRole);
        }
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRoles(roles);
        user = userRepository.save(user);
        UserResponse response = modelMapper.map(user, UserResponse.class);
        log.info("create returning " + response);
        return response;
    }

    public UserResponse edit(Long id, UserRequest userRequest) {
        log.info("edit " + id + " " + userRequest);
        UserResponse response = null;
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            if(userRequest.getFirstName() != null)
                user.setFirstName(userRequest.getFirstName());
            if(userRequest.getLastName() != null) { log.info("Update lastname " + userRequest.getLastName());
                user.setLastName(userRequest.getLastName()); }
            if(userRequest.getEmail() != null)
                user.setEmail(userRequest.getEmail());
            if(userRequest.getPassword() != null)
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            if(userRequest.getRoles() != null) {
                List<Role> roles = userRequest.getRoles().stream().map(role -> roleRepository.findByName(role).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList());
                userRoleRepository.deleteRolesForUser(id);
                user.setRoles(roles);
            }
            user = userRepository.save(user);
            response = modelMapper.map(user, UserResponse.class);
        }
        log.info("edit returning " + response);
        return response;
    }

    public boolean delete(Long id) {
        log.info("delete " + id);
        boolean success = false;
        User user = userRepository.findById(id).orElse(null);
        if(user != null) {
            userRepository.delete(user);
            success = true;
        }
        log.info("delete returning " + success);
        return success;
    }
}
