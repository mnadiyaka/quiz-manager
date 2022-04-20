package com.sigma.model.dto;

import com.sigma.model.entity.Category;
import com.sigma.model.entity.Quiz;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class QuizDto {

    private Long id;

    @NotBlank(message = "Enter name")
    @Size(min = 3, max = 20)
    @Column(name = "quiz_name")
    private String quizName;

    @Column(name = "category")
    private Category category;

    private String shortDescription;

    private LocalDateTime dateTime;

    public static QuizDto fromQuiz(Quiz quiz) {
        return new QuizDto()
                .setId(quiz.getId())
                .setQuizName(quiz.getQuizName())
                .setCategory(quiz.getCategory())
                .setShortDescription(quiz.getShortDescription())
                .setDateTime(quiz.getDateTime());
    }

    public static Quiz toQuiz(QuizDto quiz) {
        return new Quiz()
                .setId(quiz.getId())
                .setQuizName(quiz.getQuizName())
                .setCategory(quiz.getCategory())
                .setShortDescription(quiz.getShortDescription())
                .setDateTime(quiz.getDateTime());
    }
}