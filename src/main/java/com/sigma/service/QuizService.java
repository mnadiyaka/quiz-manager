package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;

import java.util.List;

public interface QuizService {

    public List<QuizDto> getAllQuizzes();

    public Quiz findQuizById(Long quizId);

    public Quiz createQuiz(QuizDto quiz, Long userId);

    public Quiz updateQuiz(QuizDto updatedQuiz, Long quizId, Long userId);

    public void deleteQuiz(Long quizId, Long userId);

    public Quiz assignLocation(final Long quizId, LocationDto locationDto);

}