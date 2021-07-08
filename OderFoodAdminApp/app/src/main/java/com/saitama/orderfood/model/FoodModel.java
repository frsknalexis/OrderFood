package com.saitama.orderfood.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodModel {
    private Long id;
    private String name;
    private String avatar;
    private Double price;
    private String description;
    private Integer status;
    private RestaurantModel restaurant;
}
