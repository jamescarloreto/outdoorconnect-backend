package com.outdoor.connect.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.outdoor.connect.exception.NoParticipantException;
import com.outdoor.connect.model.Participant;
import com.outdoor.connect.repository.ParticipantRepository;
import com.outdoor.connect.security.bean.UserBean;
import com.outdoor.connect.service.ParticipantService;

@Service
public class ParticipantServiceImpl implements ParticipantService {
    private static final Logger logger = LoggerFactory.getLogger(ParticipantServiceImpl.class);

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public Participant getParticipant() {
        logger.info("ParticipantServiceImpl | getParticipant | START");
        UserBean userBean;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getPrincipal().equals("anonymousUser")) {
            userBean = (UserBean) authentication.getPrincipal();

            Long principalId = userBean.getId();

            if (principalId == null) {
                return null;
            } else {
                return participantRepository.findById(principalId).orElseThrow(() -> new NoParticipantException());
            }
        } else {     
            return null;
        }
    }

    @Override
    public Map<String, Object> create(Participant participant) {
        logger.info("ParticipantServiceImpl | create | START");

        Map<String, Object> map = new HashMap<>();

        try {
            Participant newParticipant = participantRepository.save(participant);

            map.put("participant", newParticipant);
            map.put("status", HttpStatus.OK);
            
        } catch (Exception e) {
            logger.error("ParticipantServiceImpl | create | error: " + e.getMessage());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return map;
    }
}
