package com.orderfood.webservice.repository;

import com.orderfood.webservice.entity.FoodEntity;
import com.orderfood.webservice.entity.RestaurantEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

    List<FoodEntity> findByRestaurant(RestaurantEntity restaurant);

    void deleteByIdAndRestaurant(Long id, RestaurantEntity restaurantEntity);

    List<FoodEntity> findByRestaurant_IdAndStatus(long id, Integer status);

    List<FoodEntity> findByStatus(Integer status);
    List<FoodEntity> findByStatus(Integer status, Pageable pageable);
    FoodEntity findByIdAndStatus(Long id, Integer status);
}
