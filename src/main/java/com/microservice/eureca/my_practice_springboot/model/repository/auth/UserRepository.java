package com.microservice.eureca.my_practice_springboot.model.repository.auth;

import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntitiesByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select u from UserEntity u")
    Optional<List<UserEntity>> findAllUsersEntity();

}
