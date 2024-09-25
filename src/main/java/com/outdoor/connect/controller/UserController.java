package com.outdoor.connect.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.outdoor.connect.model.Users;
import com.outdoor.connect.security.service.JwtService;
import com.outdoor.connect.service.UserService;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody Users userCreate) {
        logger.info("UserController | createUser | START");
        logger.info("UserController | createUser : " + userCreate.toString());
        Map<String, Object> map = userService.create(userCreate);

        return new ResponseEntity<>(map.get("user"), (HttpStatusCode) map.get("status"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Users users) {
        String token = null;
        HttpStatusCode status = null;

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(),
                            users.getPassword()));

            if (authentication.isAuthenticated()) {
                token = jwtService.generateToken(users.getUsername());
                status = HttpStatus.OK;
            } else {
                status = HttpStatus.UNAUTHORIZED;
            }

        } catch (BadCredentialsException e) {
            logger.error("UserController | login | error: " + e);
            status = HttpStatus.UNAUTHORIZED;
        } catch (AuthenticationException e) {
            logger.error("UserController | login | error: " + e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(token, status);
    }
}
