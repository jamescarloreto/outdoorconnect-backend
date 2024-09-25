package com.outdoor.connect.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.outdoor.connect.model.Role;
import com.outdoor.connect.service.RoleService;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@Controller
@RequestMapping("/ad/role")
public class RoleController {
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<Object> createRole(@RequestBody Role role) {
        logger.info("RoleController | createRole | START");
        logger.info("RoleController | roleName: " + role.getRoleName());
        Map<String, Object> map = roleService.create(role);

        return new ResponseEntity<>(map.get("role"), (HttpStatusCode) map.get("status"));
    }
}
