package com.outdoor.connect.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.outdoor.connect.model.EventType;
import com.outdoor.connect.repository.EventTypeRepository;
import com.outdoor.connect.service.EventTypeService;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@Service
public class EventTypeServiceImpl implements EventTypeService {
    private static final Logger logger = LoggerFactory.getLogger(EventTypeServiceImpl.class);

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Override
    public Map<String, Object> create(EventType createEventType) {
        logger.info("EventTypeServiceImpl | create | START");
        
        Map<String, Object> map = new HashMap<>();
        try {

            if (createEventType == null) {
                logger.error("create | eventType is null");

                map.put("status", HttpStatus.BAD_REQUEST);

                return map;
            }

            logger.error("create | eventName : " + createEventType.getEventTypeName());

            EventType eventType = EventType.builder()
                    .eventTypeName(createEventType.getEventTypeName())
                    .build();

            EventType newEventType = eventTypeRepository.save(eventType);

            map.put("eventType", newEventType);
            map.put("status", HttpStatus.CREATED);

        } catch (Exception ex) {
            logger.error("EventTypeServiceImpl | create | error: " + ex.getMessage());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return map;
    }


    
}
