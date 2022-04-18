package com.sigma.service;

import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;
import com.sigma.model.entity.Team;

import java.util.List;

public interface QuizResultService {

    public QuizResults createRes(QuizResults newQuizResults);

    public QuizResults updateRes(QuizResultsDto newQuizResults);

    public void deleteRes(Long id);

    public List<QuizResultsDto> getAllRes();

    public QuizResults findResById(Long id);

    public List<QuizResultsDto> filterData(String id, String score);
}
