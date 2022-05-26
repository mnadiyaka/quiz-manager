package com.sigma.model.entity;

import lombok.Data;
import lombok.experimental.Accessors;

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
import java.time.LocalDateTime;

@Entity
@Table(name = "aggregation_result")
@Data
@Accessors(chain = true)
public class AggregationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "category")
    private Category category;

    @Column(name = "state")
    private State state;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    @Column(name = "addressId")
    private Long addressId;

    @Column(name = "score")
    private int score;

    @Column(name = "result")
    private int result;
}
