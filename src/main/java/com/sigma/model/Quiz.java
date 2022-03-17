package com.sigma.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Location getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Location address_id) {
        this.address_id = address_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return quizId == quiz.quizId && quizName.equals(quiz.quizName) && category == quiz.category && shortDescription.equals(quiz.shortDescription) && dateTime.equals(quiz.dateTime) && address_id.equals(quiz.address_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, quizName, category, shortDescription, dateTime, address_id);
    }
}