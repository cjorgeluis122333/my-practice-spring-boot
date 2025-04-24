package com.microservice.eureca.my_practice_springboot.model.repository.auth;

import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationRepository extends CrudRepository<UserEntity, Long> {

}
