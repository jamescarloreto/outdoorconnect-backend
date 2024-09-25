package com.outdoor.connect.service;

import java.util.Map;

import com.outdoor.connect.model.Event;

/**
 * 
 * @author James Carl Oreto
 * 
 */

public interface EventService {

    public Map<String, Object> create(Event event);

    public Map<String, Object> show(String filter);

}
