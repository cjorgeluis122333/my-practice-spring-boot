package com.microservice.eureca.my_practice_springboot.model.repository;

import com.microservice.eureca.my_practice_springboot.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationRepository extends CrudRepository<UserEntity, Long> {

}
