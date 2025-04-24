package com.microservice.eureca.my_practice_springboot.model.repository.auth;

import com.microservice.eureca.my_practice_springboot.model.entity.auth.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
