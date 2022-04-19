package com.sigma.model.dto;

import com.sigma.model.entity.Category;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.State;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class QuizDto {

    private Long id;

    @NotNull
    private String quizName;

    private Category category;

    private String shortDescription;

    private LocalDateTime dateTime;

    private State state;

    private short teamNumberLimit;

    private short participantInTeamNumberLimit;

    private short participantInTeamNumberMin;

    public static QuizDto fromQuiz(Quiz quiz) {
        return new QuizDto()
                .setId(quiz.getId())
                .setQuizName(quiz.getQuizName())
                .setCategory(quiz.getCategory())
                .setState(quiz.getState())
                .setShortDescription(quiz.getShortDescription())
                .setDateTime(quiz.getDateTime())
                .setParticipantInTeamNumberLimit(quiz.getParticipantInTeamNumberLimit())
                .setTeamNumberLimit(quiz.getTeamNumberLimit())
                .setParticipantInTeamNumberMin(quiz.getParticipantInTeamNumberMin());
    }

    public static Quiz toQuiz(QuizDto quiz) {
        return new Quiz()
                .setId(quiz.getId())
                .setQuizName(quiz.getQuizName())
                .setCategory(quiz.getCategory())
                .setState(quiz.getState())
                .setShortDescription(quiz.getShortDescription())
                .setDateTime(quiz.getDateTime())
                .setParticipantInTeamNumberLimit(quiz.getParticipantInTeamNumberLimit())
                .setTeamNumberLimit(quiz.getTeamNumberLimit())
                .setParticipantInTeamNumberMin(quiz.getParticipantInTeamNumberMin());
    }
}