package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;

import java.util.List;
import java.util.Set;

public interface QuizService {

    public Set<QuizDto> getAllQuizzes();

    public Quiz findQuizById(Long quizId);

    public Quiz createQuiz(QuizDto quiz, Long userId);

    public Quiz updateQuiz(QuizDto updatedQuiz, Long quizId, Long userId);

    public void deleteQuiz(Long quizId, Long userId);

    public Quiz assignLocation(final Long quizId, LocationDto locationDto);

}