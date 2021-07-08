package com.orderfood.webservice.repository;

import com.orderfood.webservice.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findByUser_Username(String userName);

    CartEntity findByUser_UsernameAndFood_Id(String user_username, Long food_id);

    void deleteByUser_UsernameAndFood_Id(String user_username, Long food_id);


}
