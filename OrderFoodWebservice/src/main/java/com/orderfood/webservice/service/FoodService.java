package com.orderfood.webservice.service;

import com.orderfood.webservice.entity.FoodEntity;
import com.orderfood.webservice.entity.RestaurantEntity;
import com.orderfood.webservice.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    public void save(FoodEntity foodEntity) {
        foodRepository.save(foodEntity);
    }

    public List<FoodEntity> findByRestaurant(RestaurantEntity restaurantEntity) {
        return foodRepository.findByRestaurant(restaurantEntity);
    }

    @Transactional
    public void deleteByIdAndRestaurant(Long id, RestaurantEntity restaurantEntity) {
        foodRepository.deleteByIdAndRestaurant(id, restaurantEntity);
    }

    public List<FoodEntity> getTop10(Pageable pageable) {
        return foodRepository.findByStatus(0,pageable);
    }

    public List<FoodEntity> findAll() {
        return foodRepository.findByStatus(0);
    }

    public FoodEntity findById(long id) {
        return foodRepository.findByIdAndStatus(id,0);
    }

    public List<FoodEntity> findByRestaurant_Id(long id) {
        return foodRepository.findByRestaurant_IdAndStatus(id,0);
    }
}
