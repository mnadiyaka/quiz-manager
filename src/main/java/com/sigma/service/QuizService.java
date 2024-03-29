package com.sigma.service;

import com.sigma.exception.QuizException;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Location;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.State;
import com.sigma.model.entity.Team;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface QuizService {

    /**
     * Finds list of Quizzes
     *
     * @return List of Quizzes
     */
    List<QuizDto> getAllQuizzes();

    /**
     * Finds {@link Quiz} by entered existing id
     *
     * @param quizId Entered id
     * @return Found Quiz
     * @throws EntityNotFoundException If entered id does not exist
     */
    Quiz findQuizById(Long quizId);

    /**
     * Creates new {@link Quiz}, entered in {@link QuizDto}
     *
     * @param quiz New Quiz data
     * @return Created new Quiz
     */
    Quiz createQuiz(QuizDto quiz);

    /**
     * Allows updating {@link Quiz} with entered new data in {@link QuizDto} (quizName, category, description, dateTime, address)
     *
     * @param updatedQuiz New data
     * @param quizId      Possible Quiz Id
     * @return Updated quiz
     * @throws EntityNotFoundException If Quiz is not found by this id
     */
    Quiz updateQuiz(QuizDto updatedQuiz, Long quizId);

    /**
     * Deletes {@link Quiz} by id
     *
     * @param quizId Entered id
     * @throws EntityNotFoundException If Quiz is not found
     */
    void deleteQuiz(Long quizId);

    /**
     * Assign existing {@link Team} for a {@link Quiz} (works in collaboration with {@link TeamService})
     *
     * @param quizId Existing Quiz id
     * @param teamId Existing Team id
     * @throws EntityNotFoundException If entered Quiz id does not exist
     * @throws QuizException           If Quiz is not {@link State}.ANNOUNCED yet
     * @throws QuizException           If Max Number of teams is reached
     * @throws QuizException           If Number of Participants doesn't suit criteria
     */
    void applyForQuiz(final Long quizId, final Long teamId);

    /**
     * Changes {@link State}, entered in input, for selected Quiz
     *
     * @param quizId Chosen {@link Quiz}
     * @param state  New {@link State}
     * @throws EntityNotFoundException If entered Quiz id does not exist
     */
    void changeQuizState(Long quizId, String state);

    /**
     * Adds existing {@link Location} to selected {@link Quiz}
     *
     * @param quizId Quiz's id
     * @param locId  Location's id
     * @return Updated Location
     * @throws EntityNotFoundException If entered Quiz id does not exist
     * @throws EntityNotFoundException If entered Location id does not exist
     */
    Quiz assignLocation(final Long quizId, final Long locId);
}