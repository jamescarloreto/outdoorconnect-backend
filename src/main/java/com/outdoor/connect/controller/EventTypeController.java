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
import org.springframework.web.bind.annotation.RestController;

import com.outdoor.connect.model.EventType;
import com.outdoor.connect.service.EventTypeService;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@RestController
@RequestMapping("/ad/event-type")
public class EventTypeController {
    private static final Logger logger = LoggerFactory.getLogger(EventTypeController.class);

    @Autowired
    private EventTypeService eventTypeService;

    @PostMapping("/create")
    public ResponseEntity<Object> createEventType(@RequestBody EventType eventType) {
        logger.info("EventTypeController | eventType | START");
        Map<String, Object> map = eventTypeService.create(eventType);

        return new ResponseEntity<>(map.get("eventType"), (HttpStatusCode) map.get("status"));
    }
}
