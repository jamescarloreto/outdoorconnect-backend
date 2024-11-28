package com.outdoor.connect.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model of Participant
 * 
 * @author James Carl Oreto
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "oc_participant")
public class Participant implements Serializable {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
	private Users users;

	private String name;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "oc_participants_liked_activities", joinColumns = @JoinColumn(name = "participantId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "event_type_id", referencedColumnName = "id"))
	private List<EventType> likedActivities;

	private String verifiedEmail;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "oc_participants_event_attended", joinColumns = @JoinColumn(name = "participantId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
	private List<Event> eventAttended;

	@JsonIgnore
	private int participantRating;

	@JsonIgnore
	private Boolean isCommentAllowed;

}
