
package com.sigma.model.dto;

import com.sigma.model.entity.QuizResults;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class QuizResultsDto {

    @NotNull
    private Long id;

    private String quizName;

    private String teamName;
    @NotNull
    private int score;

    private int avgScore;

    public static QuizResultsDto fromQuizResult(QuizResults quizResults) {
        return new QuizResultsDto()
                .setId(quizResults.getId())
                .setQuizName(quizResults.getQuiz().getQuizName())
                .setTeamName(quizResults.getTeam().getTeamName())
                .setScore(quizResults.getScore());
    }
}