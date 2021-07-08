package com.orderfood.webservice.service;

import com.orderfood.webservice.entity.RestaurantEntity;
import com.orderfood.webservice.entity.UserEntity;
import com.orderfood.webservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    public void register(RestaurantEntity restaurantEntity) {
        restaurantRepository.saveAndFlush(restaurantEntity);
    }

    public RestaurantEntity findOneByUser(UserEntity userEntity) {
        return restaurantRepository.findOneByUser(userEntity);
    }

    public List<RestaurantEntity> findAll() {
        return restaurantRepository.findAll();
    }

    public RestaurantEntity findById(long id) {
        return restaurantRepository.findById(id).get();
    }

    public void save(RestaurantEntity restaurantEntity) {
        restaurantRepository.saveAndFlush(restaurantEntity);
    }
}
