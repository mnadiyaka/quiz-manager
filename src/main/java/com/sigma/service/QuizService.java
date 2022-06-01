package com.sigma.service;

import com.sigma.exception.QuizException;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface QuizService {

    /**
     * Finds list of Quizzes
     * @return List of Quizzes
     */
    List<QuizDto> getAllQuizzes();

    /**
     * Finds Quiz by entered existing id
     * @param quizId Entered id
     * @return Found Quiz
     * @throws EntityNotFoundException If entered id does not exist
     */
    Quiz findQuizById(Long quizId);

    /**
     * Creates new Quiz from entered Data
     * @param quiz New Quiz data
     * @return Created new Quiz
     */
    Quiz createQuiz(QuizDto quiz);

    /**
     * Allows updating Quiz
     * @param updatedQuiz New data
     * @param quizId Possible Quiz Id
     * @return Updated quiz
     * @throws EntityNotFoundException If Quiz is not found by this id
     */
    Quiz updateQuiz(QuizDto updatedQuiz, Long quizId);

    /**
     * Deletes Quiz by id
     * @param quizId Entered id
     * @throws EntityNotFoundException If Result is not found
     */
    void deleteQuiz(Long quizId);

    /**
     * Works with teamService to assign existing Team for a Quiz
     * @param quizId Existing Quiz id
     * @param teamId Existing Team id
     * @throws EntityNotFoundException If entered Quiz id does not exist
     * @throws QuizException If Quiz is not ANNOUNCED yet
     * @throws QuizException If Max Number of teams is reached
     * @throws QuizException If Number of Participants doesn't suit criteria
     */
    void applyForQuiz(final Long quizId, final Long teamId);

    /**
     * Changes state for chosen Quiz
     * @param quizId Chosen quiz
     * @param state New state
     * @throws EntityNotFoundException If entered Quiz id does not exist
     */
    void changeQuizState(Long quizId, String state);

    /**
     * Adds Location to existing quiz
     * @param quizId Quiz's id
     * @param locId Location's id
     * @return Updated /location
     * @throws EntityNotFoundException If entered Quiz id does not exist
     * @throws EntityNotFoundException If entered Location id does not exist
     */
    Quiz assignLocation(final Long quizId, final Long locId);
}