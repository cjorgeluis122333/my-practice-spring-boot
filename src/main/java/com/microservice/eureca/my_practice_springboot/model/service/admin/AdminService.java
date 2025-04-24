package com.microservice.eureca.my_practice_springboot.model.service.admin;


import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import com.microservice.eureca.my_practice_springboot.model.exception.UserNotFoundException;

import java.util.List;
public interface AdminService {
    List<UserEntity> findAllUsers();

    //                       Admin methode
    boolean deleteUser(long  id);

    UserEntity updateUser(long idToUpdate, UserEntity user) throws UserNotFoundException, UserNotFoundException;

    UserEntity insertUser(UserEntity user);

}
