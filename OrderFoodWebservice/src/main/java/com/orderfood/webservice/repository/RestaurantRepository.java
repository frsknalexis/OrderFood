package com.orderfood.webservice.repository;

import com.orderfood.webservice.entity.RestaurantEntity;
import com.orderfood.webservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    RestaurantEntity findOneByUser(UserEntity userEntity);
}
