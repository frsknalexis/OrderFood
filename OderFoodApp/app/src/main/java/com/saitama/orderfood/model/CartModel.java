package com.saitama.orderfood.model;

public class CartModel {
    private Long id;
    private Integer amount;
    private FoodModel food;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public FoodModel getFood() {
        return food;
    }

    public void setFood(FoodModel food) {
        this.food = food;
    }
}
