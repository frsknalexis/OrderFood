package com.orderfood.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String avatar;
    private Double price;
    private String description;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RestaurantEntity restaurant;
}
