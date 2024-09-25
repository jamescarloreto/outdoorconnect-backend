package com.outdoor.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outdoor.connect.model.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}
