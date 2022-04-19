package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;

import java.util.Set;

public interface QuizService {

    Set<QuizDto> getAllQuizzes();

    Quiz findQuizById(Long quizId);

    Quiz createQuiz(QuizDto quiz, Long userId);

    Quiz updateQuiz(QuizDto updatedQuiz, Long quizId, Long userId);

    void deleteQuiz(Long quizId, Long userId);

    Quiz assignLocation(final Long quizId, LocationDto locationDto);

}