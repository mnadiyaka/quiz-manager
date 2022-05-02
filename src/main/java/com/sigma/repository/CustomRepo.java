package com.sigma.repository;

import com.sigma.model.dto.FilterDto;
import com.sigma.model.entity.QuizResults;

import java.util.List;
import java.util.Map;

public interface CustomRepo {

    List<QuizResults> findResultsWithCustomQuery(FilterDto data);
}