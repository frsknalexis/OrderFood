package com.orderfood.webservice.service;

import com.orderfood.webservice.entity.CartEntity;
import com.orderfood.webservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    public List<CartEntity> getAllByUser(String userName){

        return cartRepository.findByUser_Username(userName);

    }
public CartEntity findByUserNameAndIdFood(String userName, long idFood){
        return cartRepository.findByUser_UsernameAndFood_Id(userName,idFood);
}
    public CartEntity save(CartEntity cartEntity){
      return   cartRepository.save(cartEntity);
    }
    public void deleteByIdProductAndUserName(long idFood, String userName){
        cartRepository.deleteByUser_UsernameAndFood_Id(userName,idFood);
    }
    public void deleteById(long id){
        cartRepository.deleteById(id);
    }
}
