package com.sigma.model.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "participants")
@Data
@Accessors(chain = true)
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "participant_id")
    private Long id;

    @NotBlank(message = "Enter firstname")
    @Size(min = 3, max = 20)
    @Column(name = "firstname")
    private String firstname;

    @NotBlank(message = "Enter firstname")
    @Size(min = 3, max = 20)
    @Column(name = "lastname")
    private String lastname;

    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    @JoinColumn(name = "team_id", updatable = false, insertable = false)
    private Team team;

    @Column(name = "team_id")
    private Long teamId;
}