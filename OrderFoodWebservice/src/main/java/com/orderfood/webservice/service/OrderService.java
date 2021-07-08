package com.orderfood.webservice.service;

import com.orderfood.webservice.entity.OrderEntity;
import com.orderfood.webservice.entity.RestaurantEntity;
import com.orderfood.webservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderDetailRepository;


    public List<OrderEntity> findByRestaurantAndStatus(RestaurantEntity restaurantEntity, Integer status) {
        return orderDetailRepository.findByFood_RestaurantAndStatus(restaurantEntity, status);
    }

    public void save(OrderEntity orderEntity) {
        orderDetailRepository.save(orderEntity);
    }

    public List<OrderEntity> getOrderOfUser(String userName, int status){
       return orderDetailRepository.findByUser_UsernameAndStatus(userName,status);
    }
}
