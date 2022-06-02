package com.sigma.repository;

import com.sigma.model.dto.AggregationStatisticResDto;
import com.sigma.model.dto.AggregationStatisticsDto;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.entity.QuizResults;

import java.util.List;
import java.util.Objects;

public interface CustomQuizResultsStatisticsRepo {

    List<QuizResults> findResultsWithCustomQuery(QuizResultsSearchDto data);

    List<Object[]> findQuizResultAggregatedData(AggregationStatisticsDto data);
}