package com.outdoor.connect.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.outdoor.connect.model.Event;
import com.outdoor.connect.model.EventType;
import com.outdoor.connect.model.Participant;
import com.outdoor.connect.repository.EventRepository;
import com.outdoor.connect.repository.EventTypeRepository;
import com.outdoor.connect.repository.ParticipantRepository;
import com.outdoor.connect.service.EventService;
import com.outdoor.connect.utils.UserUtils;
;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@Service
public class EventServiceImpl implements EventService {
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Override
    public Map<String, Object> create(Event event) {
        logger.info("EventServiceImpl | create | START");

        Map<String, Object> map = new HashMap<>();
        try {

            if (event == null) {
                logger.error("create | event is null");

                map.put("status", HttpStatus.BAD_REQUEST);

                return map;
            }

            logger.error("create | eventName : " + event.getEventName());

            EventType eventType = eventTypeRepository.findByEventTypeName(event.getEventType().getEventTypeName());

            if (eventType != null) {
                logger.error("eventTypeName : " + eventType.getEventTypeName());
                event.setEventType(eventType);
            }

            event.setCreationDate(LocalDate.now());
            event.setIsActiveEvent(true);

            Event newEvent = eventRepository.save(event);

            map.put("event", newEvent);
            map.put("status", HttpStatus.CREATED);

        } catch (Exception ex) {
            logger.error("EventTypeServiceImpl | create | error: " + ex.getMessage());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return map;
    }

    @Override
    public Map<String, Object> show(String filter) {
        logger.info("EventServiceImpl | show | START");

        Map<String, Object> map = new HashMap<>();
        List<Event> activeEvent;

        try {
            Participant participant = UserUtils.getParticipant();
            
            if (participant != null) {

                List<Long> eventTypeIds = participant.getLikedActivities().stream().map(eventType -> eventType.getId()).collect(Collectors.toList());

                activeEvent = eventRepository.findActiveEventByEventTypeId(eventTypeIds);
            } else {
                activeEvent = eventRepository.findByIsActiveEvent(true);
            }
            
            
            // if (filter == null) { Do not erase
            //     logger.error("show | event is null");
            
            //     map.put("status", HttpStatus.BAD_REQUEST);
            
            //     return map;
            // }
            
            // logger.error("show | filter : " + filter);
            
            // EventType eventType = eventTypeRepository.findByEventTypeName(event.getEventType().getEventTypeName());
            
            // if (eventType != null) {
                //     logger.error("eventTypeName : " + eventType.getEventTypeName());
                //     event.setEventType(eventType);
                // }
                
                // event.setCreationDate(LocalDate.now());
                // event.setIsActiveEvent(true);
                
                // Event newEvent = eventRepository.save(event);
                
            map.put("events", activeEvent);
            map.put("status", HttpStatus.OK);

        } catch (Exception ex) {
            logger.error("EventTypeServiceImpl | show | error: " + ex.getMessage());
            map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return map;
    }

}
