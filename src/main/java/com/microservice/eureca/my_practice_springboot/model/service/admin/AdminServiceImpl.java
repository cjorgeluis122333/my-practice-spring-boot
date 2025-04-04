package com.microservice.eureca.my_practice_springboot.model.service.admin;

import com.microservice.eureca.my_practice_springboot.model.entity.UserEntity;
import com.microservice.eureca.my_practice_springboot.model.exception.UserNotFoundException;
import com.microservice.eureca.my_practice_springboot.model.repository.UserRepository;
import com.microservice.eureca.my_practice_springboot.model.service.auth.AuthenticationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    final UserRepository userRepository;
    final AuthenticationService authentication;
    final BCryptPasswordEncoder encoder;

    public AdminServiceImpl(UserRepository userRepository, AuthenticationService service, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.authentication = service;
        this.encoder = encoder;
    }


    @Override
    @Transactional
    public UserEntity updateUser(long idToUpdate, UserEntity user) throws UserNotFoundException {
        if (userRepository.findById(idToUpdate).isEmpty()) {
            throw new UserNotFoundException();
        }
        user.setId(idToUpdate);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty())
            return false;
        else {
            userRepository.delete(userEntity.orElseThrow());
            return true;
        }
    }

    @Override
    @Transactional
    public UserEntity insertUser(UserEntity user) {
        return authentication.register(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserEntity> findAllUsers() {
        return userRepository.findAllUsersEntity().orElseThrow();
    }
}
