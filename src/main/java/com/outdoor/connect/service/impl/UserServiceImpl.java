package com.outdoor.connect.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.outdoor.connect.exception.UserNotFoundException;
import com.outdoor.connect.model.Role;
import com.outdoor.connect.model.Users;
import com.outdoor.connect.repository.RoleRepository;
import com.outdoor.connect.repository.UserRepository;
import com.outdoor.connect.security.bean.UserBean;
import com.outdoor.connect.service.UserService;
import com.outdoor.connect.utils.MailUtils;
import com.outdoor.connect.utils.StringUtils;
import com.outdoor.connect.utils.UserUtils;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailUtils mailUtils;

    @Override
    public UserDetails findByUsername(String username) {

        Optional<Users> user = userRepository.findByUsername(username);

        return user.map(UserBean::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username : " + username + " not found!"));
    }

    @Override
    public Map<String, Object> create(Users userCreate) {
        logger.info("UserServiceImpl | create | START");

        Map<String, Object> map = new HashMap<>();
        try {

            if (userCreate == null) {
                logger.error("create | userDto is null");

                map.put("status", HttpStatus.BAD_REQUEST);

                return map;
            }

            // if (userRepository.existsById(userCreate.getId())) {
            //     logger.error("create | userDto is exists");

            //     map.put("status", HttpStatus.BAD_REQUEST);

            //     return map;
            // }

            logger.info("create | username : " + userCreate.getUsername());

            List<Role> newRole = assignUserRole(userCreate.getRoles());

            Users user = Users.builder()
                    .username(userCreate.getUsername())
                    .password(passwordEncoder.encode(userCreate.getPassword()))
                    .emailAddress(userCreate.getEmailAddress())
                    .roles(newRole)
                    .build();

            Users newUser = userRepository.save(user);
            
            sendMailVerificationToUser(newUser);
            map.put("user", newUser);
            map.put("status", HttpStatus.CREATED);

        } catch (Exception ex) {
            logger.error("UserServiceImpl | create | error: " + ex.getMessage());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return map;
    }

    private List<Role> assignUserRole(List<Role> roles) {

        List<Role> newRole = new ArrayList<>();
        roles.forEach(role -> {

            Role foundRole = roleRepository.findByRoleName(role.getRoleName());

            if (foundRole == null) {
                newRole.add(new Role(role.getRoleName()));
            } else {
                newRole.add(foundRole);
            }
        });

        return newRole;
    }

    @Override
    public Users getParticipantCredential(Long id) {
        Optional<Users> user = null;
        try {
            Long principal = id == null ? UserUtils.GetPrincipalId() : id;
    
            logger.info("UserUtils | GetParticipantCredential | Principal: " + principal);
    
            user = Optional.ofNullable(userRepository.findById(principal).orElseThrow(() -> new UserNotFoundException()));
            
            logger.info("UserUtils | GetParticipantCredential | user: " + user);

        } catch (Exception e) {
            logger.error("UserUtils | GetParticipantCredential | error: " + e.getMessage());
        }

        return user.get();
    }

    @Override
    public void sendMailVerificationToUser(Users user) {

        user = user == null ? getParticipantCredential(UserUtils.GetPrincipalId()) : user;
            
        if (user == null) {
            throw new NullPointerException("user is null");
        } else {
            user.setVerificationCode(StringUtils.Random6DigitCode());
            user.setVerificationCodeExpirationDate(LocalDateTime.now().plusMinutes(10));

            userRepository.save(user);
        }

        mailUtils.sendMailUserVerification(user);
    }
}
