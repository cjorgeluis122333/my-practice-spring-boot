package com.microservice.eureca.my_practice_springboot.model.service.auth;


import com.microservice.eureca.my_practice_springboot.model.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface

UserService {
    List<UserEntity> findAllUsers ();
    Optional<UserEntity> findByUsername (String username);
    boolean existsByUsername (String username);
    UserEntity save (UserEntity user);
}
