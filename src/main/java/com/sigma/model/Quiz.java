package com.sigma.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "quiz_id")
    private int quizId;

    @Column(name = "quiz_name")
    private String quizName;


    @Column(name = "category")
    private Category category;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    @OneToOne
    @Column(name = "address_id")
    private Location address_id;

}