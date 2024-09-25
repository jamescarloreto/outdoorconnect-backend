package com.outdoor.connect.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.outdoor.connect.model.Event;
import com.outdoor.connect.service.EventService;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@Controller
@RequestMapping("/event")
public class EventController {
    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Event event) {

        logger.info("EventTypeController | eventType | START");
        Map<String, Object> map = eventService.create(event);

        return new ResponseEntity<>(map.get("event"), (HttpStatusCode) map.get("status"));
    }

    @GetMapping("/{filter}")
    public ResponseEntity<Object> show(@PathVariable String filter) {

        logger.info("EventTypeController | eventType | START");
        Map<String, Object> map = eventService.show(filter);

        return new ResponseEntity<>(map.get("events"), (HttpStatusCode) map.get("status"));
    }

    

}
