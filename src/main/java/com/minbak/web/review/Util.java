package com.minbak.web.review;

import com.minbak.web.spring_security.CustomUserDetails;
import org.springframework.stereotype.Component;

@Component
public class Util {
    public static boolean checkAuthority(CustomUserDetails userDetails, int userId) {
        if(userDetails != null) {
            if (userDetails.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }
}