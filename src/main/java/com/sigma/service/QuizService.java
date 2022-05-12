package com.sigma.service;

import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;

import java.util.List;

public interface QuizService {

    public List<QuizDto> getAllQuizzes();

    public Quiz findQuizById(Long quizId);

    public Quiz createQuiz(QuizDto quiz);

    public Quiz updateQuiz(QuizDto updatedQuiz, Long quizId);

    public void deleteQuiz(Long quizId);

    public void applyForQuiz(final Long quizId, final Long teamId);

    public void changeQuizState(Long quizId, String state);
}