package com.sigma.service;

import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.QuizResults;

import java.util.Set;

public interface QuizResultService {

    QuizResults createRes(QuizResults newQuizResults);

    QuizResults updateRes(QuizResultsDto newQuizResults);

    void deleteRes(Long id);

    Set<QuizResultsDto> getAllRes();

    QuizResults findResById(Long id);

    Set<QuizResultsDto> filterData(String id, String score);
}
