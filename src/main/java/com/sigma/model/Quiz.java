package com.sigma.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "quizzes")
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

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "location_id")
    private Location addressId;

    @ManyToMany(mappedBy = "quizzes")
    private List<Team> teams;

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
        return addressId;
    }

    public void setAddress_id(Location address_id) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return quizId == quiz.quizId && quizName.equals(quiz.quizName) && category == quiz.category && shortDescription.equals(quiz.shortDescription) && dateTime.equals(quiz.dateTime) && addressId.equals(quiz.addressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, quizName, category, shortDescription, dateTime, addressId);
    }
}