package com.orderfood.webservice.util;

import com.orderfood.webservice.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static MyUserDetails getPrincipal() {
        try {
            return (MyUserDetails) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
