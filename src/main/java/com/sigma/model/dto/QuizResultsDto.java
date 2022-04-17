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

    @NotNull
    private int totalScore;

    public static QuizResults toQuizResult(QuizResultsDto quizResultsDto) {
        return new QuizResults()
                .setId(quizResultsDto.getId())
                .setTotalScore(quizResultsDto.getTotalScore());
    }
}
