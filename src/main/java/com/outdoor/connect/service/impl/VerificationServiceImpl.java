package com.outdoor.connect.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.outdoor.connect.service.UserService;
import com.outdoor.connect.service.VerificationService;

@Service
public class VerificationServiceImpl implements VerificationService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationServiceImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public void reSendVerification() {
        
        Map<String, Object> map = new HashMap<>();
        try {
            userService.sendMailVerificationToUser(null);

            map.put("success", true);
            map.put("status", HttpStatus.OK);
        
        } catch (Exception ex) {
            logger.error("EventTypeServiceImpl | show | error: " + ex.getMessage());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
