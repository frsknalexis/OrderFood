package com.saitama.orderfood.model;

import java.util.Date;

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


    public double getTotalPrice() {
        return amount * price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public FoodModel getFood() {
        return food;
    }

    public void setFood(FoodModel food) {
        this.food = food;
    }


}
