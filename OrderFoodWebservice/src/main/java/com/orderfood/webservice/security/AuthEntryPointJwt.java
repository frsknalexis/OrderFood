package com.orderfood.webservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orderfood.webservice.dto.JsonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer().writeValue(response.getOutputStream(), new JsonResult<String>().error("Vui lòng đăng nhập"));
    }
}
