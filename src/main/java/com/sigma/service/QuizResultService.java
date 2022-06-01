package com.sigma.service;

import com.sigma.exception.QuizException;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;
import com.sigma.model.entity.State;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

public interface QuizResultService {

    /**
     * Creates new {@link QuizResults} from entered data in {@link QuizResultsDto}
     *
     * @param newQuizResults New Result data
     * @return Created new Result
     */
    QuizResults createRes(QuizResults newQuizResults);

    /**
     * Allows updating score for existing {@link QuizResults} (changing score if needed)
     *
     * @param newQuizResults Updated {@link QuizResultsDto} data
     * @return Updated {@link QuizResults}
     * @throws EntityNotFoundException If {@link QuizResults} is not found
     * @throws QuizException           If Quiz {@link State}  is not COMPLETED yet
     */
    QuizResults updateRes(QuizResultsDto newQuizResults);

    /**
     * Deletes {@link QuizResults} by id
     *
     * @param id Result's id
     * @throws EntityNotFoundException If Result is not found
     */
    void deleteRes(Long id);

    /**
     * Finds all {@link QuizResults}, Result exist if {@link Quiz} {@link State} is COMPLETED
     *
     * @return List of Results
     */
    Set<QuizResultsDto> getAllRes();

    /**
     * Finds {@link QuizResults} by entered existing id
     *
     * @param id Result's id
     * @return Found Result
     * @throws EntityNotFoundException If entered id does not exist
     */
    QuizResults findResById(Long id);

    /**
     * Creates empty {@link QuizResults}'s Table for chosen {@link Quiz}
     *
     * @param quizId Chosen Quiz
     * @throws QuizException If Quiz {@link State} is not COMPLETED yet
     */
    void createResultsTable(Long quizId);

    /**
     * Find all {@link QuizResults} for entered {@link Quiz} id
     *
     * @param quizId Chosen Quiz
     * @return List of Results
     * @throws EntityNotFoundException If entered Quiz id does not exist
     */
    List<QuizResultsDto> findResultsByQuizId(Long quizId);

    /**
     * Finds list of {@link QuizResults}'s which can be filtered by different parameters
     *
     * @param data Entered filtered {@link QuizResultsSearchDto} data (category, location, date, period)
     * @return List of filtered Results
     */
    List<QuizResultsDto> getQuizResultsStatistics(QuizResultsSearchDto data);
}