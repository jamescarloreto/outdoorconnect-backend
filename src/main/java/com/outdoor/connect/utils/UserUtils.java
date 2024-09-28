package com.outdoor.connect.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.outdoor.connect.security.bean.UserBean;

public abstract class UserUtils {
    private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);


    public static String GetUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String username = authentication.getName();
	    
	    return username;
	}

    public static Long GetPrincipalId() {
        UserBean userBean = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getPrincipal().equals("anonymousUser")) {
            userBean = (UserBean) authentication.getPrincipal();

            return userBean.getId();
        } else {        
            return null;
        }
    }

    
}
