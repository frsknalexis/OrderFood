package com.orderfood.webservice.repository;

import com.orderfood.webservice.entity.OrderEntity;
import com.orderfood.webservice.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findOneById(Long id);

    List<OrderEntity> findByUser_UsernameAndStatus(String user_username, Integer status);

    List<OrderEntity> findByUser_RestaurantAndStatus(RestaurantEntity restaurantEntity, Integer status);

    List<OrderEntity> findByFood_RestaurantAndStatus(RestaurantEntity food_restaurant, Integer status);
}

