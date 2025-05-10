package com.microservice.eureca.my_practice_springboot.controller.rest;

import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import com.microservice.eureca.my_practice_springboot.model.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.microservice.eureca.my_practice_springboot.common.utili.RequestValidation.validation;
//Is not necessary implement login(/login) o logout(/api/logout)

@RequestMapping("/api")
@RestController()
public class AuthenticateController {

    private final AuthenticationService authenticationService;
    public AuthenticateController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    ResponseEntity<?> registerUser(@Valid @RequestBody UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return validation(bindingResult);
        return ResponseEntity.ok(authenticationService.register(user));
    }


}
