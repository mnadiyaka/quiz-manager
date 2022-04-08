package com.sigma.service;

import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;

import java.util.List;

public interface QuizService {

    public List<QuizDto> getAllQuizzes();

    public QuizDto findQuizById(Long quizId);

    public Quiz createQuiz(QuizDto quiz);

    public void updateQuiz(QuizDto updatedQuiz, Long quizId);

    public void deleteQuiz(Long quizId);
}