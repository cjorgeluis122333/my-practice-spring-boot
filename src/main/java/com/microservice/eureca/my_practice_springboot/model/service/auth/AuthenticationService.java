package com.microservice.eureca.my_practice_springboot.model.service.auth;


import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;

public interface AuthenticationService {
    UserEntity register(UserEntity newUser);
}
