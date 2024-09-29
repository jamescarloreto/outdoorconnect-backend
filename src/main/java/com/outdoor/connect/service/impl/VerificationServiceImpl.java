package com.outdoor.connect.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.outdoor.connect.dto.VerificationDto;
import com.outdoor.connect.exception.UserNotFoundException;
import com.outdoor.connect.model.Users;
import com.outdoor.connect.repository.UserRepository;
import com.outdoor.connect.service.UserService;
import com.outdoor.connect.service.VerificationService;
import com.outdoor.connect.utils.UserUtils;

@Service
public class VerificationServiceImpl implements VerificationService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

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

    @Override
    public Map<String, Object> verifyCode(String verificationCode) {
        logger.info("VerificationServiceImpl | verifyCode | START");
        
        Map<String, Object> map = new HashMap<>();
        try {
            Long principalId = UserUtils.GetPrincipalId();

            Boolean verified = userRepository.verifyCode(principalId, verificationCode, LocalDateTime.now());

            Users user = userRepository.findById(principalId).orElseThrow(() -> new UserNotFoundException("No found user in id: " + principalId));

            VerificationDto verificationDto = VerificationDto.builder()
                .isVerified(verified == null ? false : verified)
                .verifiedEmail(user.getEmailAddress())
                .build();
            
            map.put("verificationDto", verificationDto);
            map.put("status", HttpStatus.OK);
        
        } catch (Exception ex) {
            logger.error("EventTypeServiceImpl | show | error: " + ex.getMessage());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return map;
    }

    
}
