package com.sigma.model.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Data
@Accessors(chain = true)
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quiz_id")
    private Long id;

    @NotNull
    @Column(name = "quiz_name")
    private String quizName;

    @NotNull
    @Column(name = "category")
    private Category category;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    @Column(name = "state")
    private State state;

    private final short teamNumberLimit = 20;
    private final short participantInTeamNumberLimit = 10;
    private final short participantInTeamNumberMin = 5;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "location_id")
    private Location address;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "quizzes")
    private List<Team> teams;
}