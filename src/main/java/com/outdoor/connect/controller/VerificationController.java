package com.outdoor.connect.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.outdoor.connect.service.VerificationService;

@Controller
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
}
