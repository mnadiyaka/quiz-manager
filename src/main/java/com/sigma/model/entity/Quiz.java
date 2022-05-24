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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "Enter name")
    @Size(min = 3, max = 20)
    @Column(name = "quiz_name")
    private String quizName;

    @Column(name = "category")
    private Category category;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "state")
    private State state;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "location_id", updatable = false, insertable = false)
    private Location address;

    @Column(name = "address_id")
    private Long addressId;

    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "quizzes")
    private List<Team> teams;
}