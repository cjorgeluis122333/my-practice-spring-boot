package com.microservice.eureca.my_practice_springboot.model.repository.paging;

import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPagingRepository extends JpaRepository<UserEntity, Long> {
}
