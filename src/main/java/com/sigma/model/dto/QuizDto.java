package com.sigma.model.dto;

import com.sigma.model.entity.Category;
import com.sigma.model.entity.Quiz;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class QuizDto {

    private Long id;

    @NotBlank(message = "Enter name")
    @Size(min = 3, max = 20)
    private String quizName;

    private Category category;

    private String shortDescription;

    private LocalDateTime dateTime;

    private Long addressId;

    public static QuizDto fromQuiz(Quiz quiz) {
        return new QuizDto()
                .setId(quiz.getId())
                .setQuizName(quiz.getQuizName())
                .setCategory(quiz.getCategory())
                .setShortDescription(quiz.getShortDescription())
                .setDateTime(quiz.getDateTime())
                .setAddressId(quiz.getAddressId());
    }

    public static Quiz toQuiz(QuizDto quiz) {
        return new Quiz()
                .setId(quiz.getId())
                .setQuizName(quiz.getQuizName())
                .setCategory(quiz.getCategory())
                .setShortDescription(quiz.getShortDescription())
                .setDateTime(quiz.getDateTime())
                .setAddressId(quiz.getAddressId());
    }
}