package com.sigma.repository;

import com.sigma.model.dto.FilterDto;
import com.sigma.model.entity.QuizResults;

import java.util.List;

public interface CustomRepo {

    List<QuizResults> findResultsWithCustomQuery(FilterDto data);
}