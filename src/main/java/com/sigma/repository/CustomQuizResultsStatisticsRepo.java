package com.sigma.repository;

import com.sigma.model.dto.AggregationStatisticsDto;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;

import java.sql.SQLException;
import java.util.List;

public interface CustomQuizResultsStatisticsRepo {

    List<QuizResults> findResultsWithCustomQuery(QuizResultsSearchDto data);

    List<QuizResults>findQuizResultAggregatedData(AggregationStatisticsDto data) throws SQLException;
}