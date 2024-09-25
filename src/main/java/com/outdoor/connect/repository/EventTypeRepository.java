package com.outdoor.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outdoor.connect.model.EventType;

/**
 * 
 * @author James Carl Oreto
 * 
 */

public interface EventTypeRepository extends JpaRepository<EventType, Long>{

    public EventType findByEventTypeName(String eventTypeName);

}
