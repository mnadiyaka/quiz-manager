package com.sigma.model;

import javax.persistence.*;

@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="participant_id")
    private int participantId;

    @Column(name = "firstname")
    private int firstname;

    @Column(name = "lastname")
    private String lastname;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team teamId;
}
