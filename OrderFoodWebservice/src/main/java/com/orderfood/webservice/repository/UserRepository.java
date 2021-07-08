package com.orderfood.webservice.repository;

import com.orderfood.webservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findOneByUsernameAndPassword(String username, String password);

    UserEntity findOneByUsername(String username);
}
