package com.microservice.eureca.my_practice_springboot.controller.rest;

import com.microservice.eureca.my_practice_springboot.common.utili.RequestValidation;
import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import com.microservice.eureca.my_practice_springboot.model.exception.UserNotFoundException;
import com.microservice.eureca.my_practice_springboot.model.service.admin.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;


    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(adminService.findAllUsers());
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> adminRegisterUser(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return RequestValidation.validation(bindingResult);

        user.setAdmin(false);
        return ResponseEntity.ok(adminService.insertUser(user));
    }

    @PostMapping("/createAdmin")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> createAdmin(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return RequestValidation.validation(bindingResult);
        user.setAdmin(true);
        return ResponseEntity.ok(adminService.insertUser(user));
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> adminUpdateUser(@Valid @RequestBody UserEntity user, @PathVariable long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return RequestValidation.validation(bindingResult);
        UserEntity userEntity;
        try {
            userEntity = adminService.updateUser(id, user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Boolean> deleteUser(@RequestParam long id) {
        if (adminService.deleteUser(id))
            return ResponseEntity.ok(true);
        return ResponseEntity.notFound().build();
    }

}
