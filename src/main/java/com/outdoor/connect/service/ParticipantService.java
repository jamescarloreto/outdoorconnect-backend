package com.outdoor.connect.service;

import java.util.Map;

import com.outdoor.connect.model.Participant;

public interface ParticipantService {

    public Participant getParticipant();

    public Map<String, Object> create(Participant participant);
}
