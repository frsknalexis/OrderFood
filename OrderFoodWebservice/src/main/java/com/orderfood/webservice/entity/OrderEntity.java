package com.orderfood.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "order_detail")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone; // Số điện thoại
    private String address;// Địa chỉ
    private Integer amount;// Số lượng sản phẩm
    private Double price;// Giá của một sản phẩm
    @Transient
    private Double totalPrice;// Tổng giá của một sản phẩm = amount * price
    private Date orderDate;// Thời gian đặt món
    private Integer status;// Trạng thái
    /**
     * TRẠNG THÁI
     * 0. Chờ xác nhận
     * 1. Đang giao
     * 2. Đã giao
     * 3. Hủy
     **/

    @ManyToOne
    @JoinColumn(name = "food_id")
    private FoodEntity food;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public double getTotalPrice() {
        return amount * price;
    }

}
