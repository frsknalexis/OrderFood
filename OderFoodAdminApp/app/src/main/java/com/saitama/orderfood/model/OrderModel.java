package com.saitama.orderfood.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderModel {
    private Long id;
    private String phone; // Số điện thoại
    private String address;// Địa chỉ
    private Integer amount;// Số lượng sản phẩm
    private Double price;// Giá của một sản phẩm
    private Double totalPrice;// Tổng giá của một sản phẩm = amount * price
    private Date orderDate;// Thời gian đặt món
    private Integer status;// Trạng thái

    private FoodModel food;
    private UserModel user;
}
