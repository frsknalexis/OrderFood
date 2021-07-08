package com.saitama.orderfood.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModel {
    private Long id;
    private String username;
    private String password;
    private String rePassword;
    private String name;
    private Boolean sex;
    private String avatar;
    private Integer reg;

}
