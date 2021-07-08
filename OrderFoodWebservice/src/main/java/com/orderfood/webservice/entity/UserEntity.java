package com.orderfood.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Transient
    private String rePassword;
    private String name;
    private Boolean sex;
    private String avatar;
    private String tokenFCM;
    @Transient
    private Integer reg;//reg == 0: RESTAURANT || reg == 1: MEMBER

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
    private List<RoleEntity> roles;

    @JsonIgnore
    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY)
    private RestaurantEntity restaurant;
}
