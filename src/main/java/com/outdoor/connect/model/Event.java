package com.outdoor.connect.model;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
 * Model of Event
 * 
 * @author James Carl Oreto
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "oc_event")
@Entity
public class Event {

    /**
     * 
     * DO NO REMOVE THE COMMENTS, as this will fix ordering issue by;
     * 1. Uncomment @Column for the first time
     * 2. Run the application
     * 3. Execute this sql/tables/TableChange_09-24-2024_01.sql query
     * 4. Comment @Column again
     * 
     * Reference: https://forum.hibernate.org/viewtopic.php?f=6&t=974532
     */

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "a_id")
    private Long id;

    // @Column(name = "b_event_name", nullable=false)
    private String eventName;

    @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private EventType eventType;

    // @Column(name = "d_event_description", nullable=false)
    private String eventDescription;

    // @Column(name = "e_event_date", nullable=false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime eventDate;

    @JsonIgnore
    // @Column(name = "f_creation_date", nullable=false)
    private LocalDate creationDate;

    // @Column(name = "g_event_location", nullable=false)
    private String eventLocation;

    @Column(scale = 2 /* , name = "h_event_fee", nullable=true */ )
    private double eventFee;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "oc_event_participants", joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "participant_id", referencedColumnName = "id"))
    private List<Participant> participants;

    @JsonIgnore
    // @Column(name = "i_isActiveEvent", nullable=true)
    private Boolean isActiveEvent;
}
