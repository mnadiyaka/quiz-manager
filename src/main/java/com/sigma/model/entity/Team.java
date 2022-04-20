package com.sigma.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "teams")
@Data
@Accessors(chain = true)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_id")
    private Long id;

    @NotBlank(message = "Enter name")
    @Size(min = 3, max = 20)
    @Column(name = "team_name")
    private String teamName;

    @Column(name = "confirmed")
    private boolean confirmed;

    @ManyToMany
    @JoinTable(name = "enrolled_quizzes",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id"))
    @Column(name = "quiz_id")
    private List<Quiz> quizzes;

    @OneToOne
    @JoinColumn(name = "captain_id")
    private User captain;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "team", orphanRemoval = true)
    @Size(max = 9, message = "only 9 people per team")//TODO: npt like this
    private List<Participant> participants;
}