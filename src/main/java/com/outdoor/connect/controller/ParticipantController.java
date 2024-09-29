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

import com.outdoor.connect.model.Participant;
import com.outdoor.connect.service.ParticipantService;

@Controller
@RequestMapping("/participant")
public class ParticipantController {
    private static final Logger logger = LoggerFactory.getLogger(ParticipantController.class);

    @Autowired
    private ParticipantService participantService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Participant participant) {
        logger.info("ParticipantController | create | START");

        Map<String, Object> map = participantService.create(participant);

        return new ResponseEntity<>(map.get("participant"), (HttpStatusCode) map.get("status"));
    }
}
