package com.orderfood.webservice.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtToken {

    private String accessToken;
    private String tokenType = "Bearer";

    public JwtToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
