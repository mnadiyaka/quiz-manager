package com.sigma.service;

import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;
import com.sigma.model.entity.Team;

import java.util.List;
import java.util.Set;

public interface QuizResultService {

    public QuizResults createRes(QuizResults newQuizResults);

    public QuizResults updateRes(QuizResultsDto newQuizResults);

    public void deleteRes(Long id);

    public Set<QuizResultsDto> getAllRes();

    public QuizResults findResById(Long id);

    public Set<QuizResultsDto> filterData(String id, String score);
}
