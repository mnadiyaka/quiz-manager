package com.sigma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "participants")
@Data
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "participant_id")
    private int participantId;

    @Column(name = "firstname")
    private int firstname;

    @Column(name = "lastname")
    private String lastname;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;
}
