package com.sigma.service;

import com.sigma.exception.QuizException;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.QuizResults;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

public interface QuizResultService {

    /**
     * Creates new Result from entered Data in QuizResultDto
     *
     * @param newQuizResults New Result Data
     * @return Created new Result
     */
    QuizResults createRes(QuizResults newQuizResults);

    /**
     * Allows updating score for existing Result (changing score if needed)
     *
     * @param newQuizResults Updated data
     * @return Updated Quiz Result
     * @throws EntityNotFoundException If Quiz Result is not found
     * @throws QuizException           If Quiz is not COMPLETED yet
     */
    QuizResults updateRes(QuizResultsDto newQuizResults);

    /**
     * Deletes Result by id
     *
     * @param id Result's id
     * @throws EntityNotFoundException If Result is not found
     */
    void deleteRes(Long id);

    /**
     * Finds all Results, Result exist if Quiz is COMPLETED
     *
     * @return List of Results
     */
    Set<QuizResultsDto> getAllRes();

    /**
     * Finds Result by entered existing id
     *
     * @param id Result's id
     * @return Found Result
     * @throws EntityNotFoundException If entered id does not exist
     */
    QuizResults findResById(Long id);

    /**
     * Creates empty result's Table for chosen Quiz
     *
     * @param quizId Chosen Quiz
     * @throws QuizException If Quiz is not COMPLETED yet
     */
    void createResultsTable(Long quizId);

    /**
     * Find all results for entered Quiz id
     *
     * @param quizId Chosen Quiz
     * @return List of Results
     * @throws EntityNotFoundException If entered Quiz id does not exist
     */
    List<QuizResultsDto> findResultsByQuizId(Long quizId);

    /**
     * Finds list of quiz Result's which can be filtered by different parameters
     *
     * @param data Entered filtered data (category, location, date, period)
     * @return List of filtered Results
     */
    List<QuizResultsDto> getQuizResultsStatistics(QuizResultsSearchDto data);
}