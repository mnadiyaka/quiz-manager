package com.sigma.repository;

import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.entity.QuizResults;

import java.util.List;

public interface CustomRepo {

    List<QuizResults> findResultsWithCustomQuery(QuizResultsSearchDto data);
}