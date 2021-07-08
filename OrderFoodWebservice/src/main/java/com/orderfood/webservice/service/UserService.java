package com.orderfood.webservice.service;

import com.orderfood.webservice.entity.RoleEntity;
import com.orderfood.webservice.entity.UserEntity;
import com.orderfood.webservice.repository.RoleRepository;
import com.orderfood.webservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserEntity findOneByUsernameAndPassword(String username, String password) {
        return userRepository.findOneByUsernameAndPassword(username, password);
    }

    public UserEntity findOneByUsername(String username) {
        return userRepository.findOneByUsername(username);
    }

    public void register(UserEntity userEntity) {
        if (userEntity.getId() == null) {
            List<RoleEntity> roles = new ArrayList<>();
            if (userEntity.getReg() == null)
                userEntity.setReg(1);
            if (userEntity.getReg() == 0)
                roles.add(roleRepository.findByName("ROLE_ADMIN"));
            else
                roles.add(roleRepository.findByName("ROLE_MEMBER"));
            userEntity.setRoles(roles);
        }
        if (userEntity.getPassword() != null)
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
