package com.microservice.eureca.my_practice_springboot.model.service.user;

import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import com.microservice.eureca.my_practice_springboot.model.repository.auth.UserRepository;
import com.microservice.eureca.my_practice_springboot.model.repository.paging.UserPagingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPagingRepository userPagingRepository;

    public UserServiceImpl(UserRepository userRepository, UserPagingRepository userPagingRepository) {
        this.userRepository = userRepository;
        this.userPagingRepository = userPagingRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAllUsers() {
        return userRepository.findAllUsersEntity().orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserEntity> findUserWithPaging(Pageable pageable) {
        return userPagingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) throws UsernameNotFoundException {
        var userTemp = userRepository.findUserEntitiesByUsername(username);
        if (userTemp.isEmpty()) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return userTemp.get();

    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional()
    public UserEntity updateUser(UserEntity user) throws NoSuchElementException {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
}
