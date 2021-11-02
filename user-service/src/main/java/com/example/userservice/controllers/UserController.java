package com.example.userservice.controllers;

import com.example.userservice.dtos.LoginRequest;
import com.example.userservice.dtos.LoginResponse;
import com.example.userservice.dtos.UserRequest;
import com.example.userservice.dtos.UserResponse;
import com.example.userservice.entities.User;
import com.example.userservice.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws JsonProcessingException {
        log.info("login " + loginRequest);
        ResponseEntity<LoginResponse> response = null;
        LoginResponse loginResponse = userService.login(loginRequest);
        if(loginResponse != null) {
            response = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return response;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("getAllUsers");
        List<UserResponse> users = userService.getAll();
        ResponseEntity<List<UserResponse>> response = new ResponseEntity<>(users, HttpStatus.OK);
        return response;
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN') or isAuthenticated() and principal.id == #id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        log.info("getUserById " + id);
        ResponseEntity<UserResponse> response = null;
        UserResponse user = userService.getById(id);
        if(user != null) {
            response = new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        log.info("createUser " + userRequest);
        ResponseEntity<UserResponse> response = null;
        if(userService.isEmailAvailable(userRequest.getEmail())) {
            boolean wannaCreateAdmin = userService.hasRoleAdmin(userRequest);
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(!wannaCreateAdmin || (principal != null && principal instanceof User && userService.hasRoleAdmin((User) principal))) {
                UserResponse user = userService.create(userRequest);
                response = new ResponseEntity<>(user, HttpStatus.CREATED);
            } else {
                response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } else {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return response;
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN') or isAuthenticated() and principal.id == #id")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserRequest userRequest) {
        log.info("updateUser " + id + " " + userRequest);
        ResponseEntity<UserResponse> response = null;
        boolean wannaCreateAdmin = userService.hasRoleAdmin(userRequest);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!wannaCreateAdmin || (principal != null && principal instanceof User && userService.hasRoleAdmin((User)principal))) {
            UserResponse userResponse = userService.edit(id, userRequest);
            if(userResponse != null) {
                response = new ResponseEntity<>(userResponse, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            response = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return response;
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("isAuthenticated() and hasRole('ADMIN') or isAuthenticated() and principal.id == #id")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.info("deleteUser " + id);
        ResponseEntity<Void> response = null;
        boolean success = userService.delete(id);
        if(success) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
