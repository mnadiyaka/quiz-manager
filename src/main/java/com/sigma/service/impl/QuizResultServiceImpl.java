package com.sigma.service.impl;

import com.sigma.exception.QuizException;
import com.sigma.model.dto.AggregationStatisticResDto;
import com.sigma.model.dto.AggregationStatisticsDto;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;
import com.sigma.model.entity.State;
import com.sigma.model.entity.Team;
import com.sigma.repository.QuizResultsRepository;
import com.sigma.service.QuizResultService;
import com.sigma.service.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizResultServiceImpl implements QuizResultService {

    private final QuizResultsRepository quizResultsRepository;

    private final QuizService quizService;

    @Override
    public QuizResults findResById(Long id) {
        return quizResultsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No record with this id"));
    }

    @Override
    public List<QuizResultsDto> findResultsByQuizId(Long quizId) {
        List<QuizResults> res = quizResultsRepository.findQuizResultsByQuizId(quizId);
        Optional.ofNullable(res).orElseThrow(() -> new EntityNotFoundException("No quiz like that"));
        return res.stream().map(QuizResultsDto::fromQuizResult).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuizResults createRes(QuizResults newQuizResults) {
        return quizResultsRepository.save(newQuizResults);
    }

    @Override
    @Transactional
    public QuizResults updateRes(QuizResultsDto newQuizResults) {
        QuizResults quizResults = findResById(newQuizResults.getId());
        if (!quizResults.getQuiz().getState().equals(State.COMPLETED)) {
            throw new QuizException("Quiz is not CLOSED yet");
        }
        Optional.ofNullable(newQuizResults.getScore()).ifPresent(quizResults::setScore);

        return quizResultsRepository.save(quizResults);
    }

    @Override
    @Transactional
    public void deleteRes(Long id) {
        quizResultsRepository.delete(findResById(id));
    }

    @Override
    public Set<QuizResultsDto> getAllRes() {
        return quizResultsRepository.findAll().stream().map(QuizResultsDto::fromQuizResult).collect(Collectors.toSet());
    }

    @Override
    public List<QuizResultsDto> getQuizResultsStatistics(QuizResultsSearchDto data) {
        List<QuizResults> res = quizResultsRepository.findResultsWithCustomQuery(data);

        return res.stream().map(QuizResultsDto::fromQuizResult).collect(Collectors.toList());
    }

    @Override
    public List<AggregationStatisticResDto> getAggregationStatistics(AggregationStatisticsDto data) {
        return quizResultsRepository.findQuizResultAggregatedData(data).stream().map(AggregationStatisticResDto::toDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createResultsTable(Long quizId) {
        Quiz quiz = quizService.findQuizById(quizId);
        if (!quiz.getState().equals(State.COMPLETED)) {
            throw new QuizException("Quiz is not completed");
        }
        QuizResults quizResults;
        List<Team> teams = quiz.getTeams();
        for (Team team : teams) {
            quizResults = new QuizResults();
            quizResults.setQuiz(quiz);
            quizResults.setTeam(team);
            createRes(quizResults);
        }
    }
}