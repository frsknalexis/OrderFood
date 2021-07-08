package com.saitama.orderfood.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantModel {
    private Long id;
    private String name;
    private String avatar;
    private String address;
    private Integer status;
}
