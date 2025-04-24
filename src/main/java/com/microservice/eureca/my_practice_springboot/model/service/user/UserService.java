package com.microservice.eureca.my_practice_springboot.model.service.user;


import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import com.microservice.eureca.my_practice_springboot.model.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface

UserService {
    List<UserEntity> findAllUsers ();
    Page<UserEntity> findUserWithPaging (Pageable pageable);
    UserEntity findByUsername (String username) throws UserNotFoundException;
    boolean existsByUsername (String username);
    UserEntity updateUser (UserEntity user) throws UserNotFoundException;
    UserEntity save (UserEntity user);

}
