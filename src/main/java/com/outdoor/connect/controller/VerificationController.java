package com.outdoor.connect.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.outdoor.connect.service.VerificationService;

@RestController
@RequestMapping("/verify")
public class VerificationController {
    private static final Logger logger = LoggerFactory.getLogger(VerificationController.class);

    @Autowired
    private VerificationService verificationService;

    @PostMapping("/user")
    public ResponseEntity<Object> sendVerificationCode() {
        logger.info("VerificationController | sendVerificationCode | START");

        verificationService.reSendVerification();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirmation/{verificationCode}")
    public ResponseEntity<Object> confirmation(@PathVariable String verificationCode) {
        logger.info("VerificationController | confirmation | START");

        Map<String, Object> map = verificationService.verifyCode(verificationCode);
        return new ResponseEntity<>(map.get("verificationDto"), (HttpStatusCode) map.get("status"));
    }
}
