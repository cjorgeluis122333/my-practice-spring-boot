package com.microservice.eureca.my_practice_springboot.model.service.auth;

import com.microservice.eureca.my_practice_springboot.model.entity.auth.RoleEntity;
import com.microservice.eureca.my_practice_springboot.model.entity.auth.UserEntity;
import com.microservice.eureca.my_practice_springboot.model.repository.auth.RoleRepository;
import com.microservice.eureca.my_practice_springboot.model.service.user.UserService;
import com.microservice.eureca.my_practice_springboot.model.util.type.RoleEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserService userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserEntity register(UserEntity newUser) {
        ArrayList<RoleEntity> roleTemp = new ArrayList<>();

        Optional<RoleEntity> userRole = roleRepository.findByName(RoleEnum.USER.name());
        userRole.ifPresent(roleTemp::add);

        if (newUser.isAdmin()) {
            Optional<RoleEntity> adminRole = roleRepository.findByName(RoleEnum.ADMIN.name());
            adminRole.ifPresent(roleTemp::add);
        }

        newUser.setRoleEntities(roleTemp);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setPicture("default_profile_img.png");

        return userService.save(newUser);
    }

}
