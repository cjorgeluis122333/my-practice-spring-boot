package com.microservice.eureca.my_practice_springboot.controller.rest;

import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import com.microservice.eureca.my_practice_springboot.model.exception.UserNotFoundException;
import com.microservice.eureca.my_practice_springboot.model.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/userInfo")
    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> userInfo(Authentication authentication) {
        try {

            var user = userService.findByUsername(authentication.getName());
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allUser")
    @PreAuthorize(value = "hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<?> userTemp() {
        return ResponseEntity.ok(userService.findAllUsers());

    }

    @GetMapping("/users/page/{current_page}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public Page<UserEntity> usersPage(@PathVariable Integer current_page) {
        Pageable pageable = PageRequest.of(current_page, 4);
        return userService.findUserWithPaging(pageable);
    }


}

