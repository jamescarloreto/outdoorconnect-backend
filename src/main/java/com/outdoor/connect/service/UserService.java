package com.outdoor.connect.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

import com.outdoor.connect.model.Users;

/**
 * 
 * @author James Carl Oreto
 * 
 */

public interface UserService {

    public UserDetails findByUsername(String username);

    public Map<String, Object> create(Users userCreate);
}
