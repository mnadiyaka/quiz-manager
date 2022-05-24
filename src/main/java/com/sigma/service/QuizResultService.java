package com.sigma.service;

import com.sigma.model.dto.AggregationStatisticsDto;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.QuizResults;

import java.util.List;
import java.util.Set;

public interface QuizResultService {

    QuizResults createRes(QuizResults newQuizResults);

    QuizResults updateRes(QuizResultsDto newQuizResults);

    void deleteRes(Long id);

    Set<QuizResultsDto> getAllRes();

    QuizResults findResById(Long id);

    void createResultsTable(Long quizId);

    List<QuizResultsDto> findResultsByQuizId(Long quizId);

    List<QuizResultsDto> getQuizResultsStatistics(QuizResultsSearchDto data);

    List<QuizResultsDto> getAggregationStatistics(AggregationStatisticsDto data);
}