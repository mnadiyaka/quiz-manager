package com.sigma.service;

import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;
import com.sigma.model.entity.Team;

import java.util.List;

public interface QuizResultService {

    public QuizResults createRes(QuizResults newQuizResults);

    public QuizResults updateRes(QuizResults newQuizResults, Long quizResId);

    public void deleteRes(Long id);

    public List<QuizResults> getAllRes();

    public QuizResults findResById(Long id);
}
