package com.outdoor.connect.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.outdoor.connect.model.Role;
import com.outdoor.connect.repository.RoleRepository;
import com.outdoor.connect.service.RoleService;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@Service
public class RoleServiceImpl implements RoleService {
private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Map<String, Object> create(Role createRole) {
        logger.info("RoleServiceImpl | create | START");

        Map<String, Object> map = new HashMap<>();
        try {

            if (createRole == null) {
                logger.error("create | role is null");

                map.put("status", HttpStatus.BAD_REQUEST);

                return map;
            }

            logger.error("create | role : " + createRole.getRoleName());

            Role role = Role.builder()
                    .roleName(createRole.getRoleName())
                    .build();

            Role newRole = roleRepository.save(role);

            map.put("role", newRole);
            map.put("status", HttpStatus.CREATED);

        } catch (Exception ex) {
            logger.error("RoleServiceImplv | create | error: " + ex.getMessage());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return map;
    }

    
}
