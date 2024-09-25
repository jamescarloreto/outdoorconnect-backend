package com.outdoor.connect.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.outdoor.connect.model.Event;

/**
 * @author James Carl Oreto
 */

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = """
            SELECT * FROM oc_event e
            WHERE 
                e.event_type_id IN (:eventTypeIds) AND
                e.is_active_event = true
            ORDER BY
                e.event_date DESC
            """, nativeQuery = true)
    public List<Event> findActiveEventByEventTypeId(@Param("eventTypeIds") List<Long> eventTypeIds);

    @Query(value = """
            SELECT * FROM oc_event e
            WHERE 
                e.is_active_event = :isActiveEvent
            ORDER BY
                e.event_date DESC
            """, nativeQuery = true)
    public List<Event> findByIsActiveEvent(@Param("isActiveEvent") boolean isActiveEvent);

    
    
}
