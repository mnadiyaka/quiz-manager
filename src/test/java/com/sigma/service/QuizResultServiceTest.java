package com.sigma.service;

import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;
import com.sigma.model.entity.State;
import com.sigma.model.entity.Team;
import com.sigma.repository.QuizResultsRepository;
import com.sigma.service.impl.QuizResultServiceImpl;
import com.sigma.service.impl.QuizServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuizResultServiceTest {

    private QuizResultsRepository quizResultsRepository;

    private QuizResultService quizResultService;

    private final QuizService quizService = mock(QuizServiceImpl.class);

    @BeforeEach
    void setUp() {
        quizResultsRepository = mock(QuizResultsRepository.class);
        quizResultService = new QuizResultServiceImpl(quizResultsRepository, quizService);
    }

    @Test
    public void findQuizResultById_WithExistingId_ThenReturnQuizResult() {
        final QuizResults expectedResult = new QuizResults();
        when(quizResultsRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
        final QuizResults result = quizResultService.findResById(anyLong());

        Assertions.assertEquals(expectedResult, result);
        verify(quizResultsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findQuizResultById_WithNonExistingId_ThenThrowException() {
        when(quizResultsRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> quizResultService.findResById(anyLong()));
        verify(quizResultsRepository, times(1)).findById(anyLong());
    }

    @Test
    public void createQuizResult_WithQuizResult_ThenReturnNewQuizResult() {
        QuizResults expected = new QuizResults();

        when(quizResultsRepository.save(expected)).thenReturn(expected);

        QuizResults actual = quizResultService.createRes(expected);
        Assertions.assertEquals(expected, actual);
        verify(quizResultsRepository, times(1)).save(expected);
    }

    @Test
    public void updateQuizResult_WithQuizDto_ThenReturnUpdatedQuiz() {
        QuizResults quizResults = new QuizResults();
        quizResults.setId(1L);
        quizResults.setQuiz(new Quiz().setState(State.COMPLETED).setQuizName("name"));
        quizResults.setTeam(new Team().setTeamName("name"));
        when(quizResultsRepository.findById(anyLong())).thenReturn(Optional.of(quizResults));

        QuizResults oldQuiz = quizResultService.findResById(anyLong());
        oldQuiz.setScore(111);

        when(quizResultsRepository.save(oldQuiz)).thenReturn(oldQuiz);

        QuizResults actual = quizResultService.updateRes(QuizResultsDto.fromQuizResult(quizResults));
        Assertions.assertEquals(oldQuiz, actual);
        verify(quizResultsRepository, times(1)).save(quizResults);
    }

    @Test
    public void deleteQuizResult_WithQuizResultId() {
        QuizResults expected = new QuizResults();
        expected.setId(1L);
        when(quizResultsRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        doNothing().when(quizResultsRepository).delete(any());

        quizResultService.deleteRes(expected.getId());
        verify(quizResultsRepository).delete(eq(expected));
    }

    @Test
    public void getAllResults_ThenReturnList() {
        List<QuizResults> quizzes = new ArrayList<>();
        quizzes.add(new QuizResults().setId(1L).setQuiz(new Quiz().setQuizName("name")).setTeam(new Team().setTeamName("name")));
        quizzes.add(new QuizResults().setId(2L).setQuiz(new Quiz().setQuizName("name")).setTeam(new Team().setTeamName("name")));
        quizzes.add(new QuizResults().setId(3L).setQuiz(new Quiz().setQuizName("name")).setTeam(new Team().setTeamName("name")));
        when(quizResultsRepository.findAll()).thenReturn(quizzes);

        Set<QuizResultsDto> actual = quizResultService.getAllRes();
        Assertions.assertEquals(quizzes.stream().map(QuizResultsDto::fromQuizResult).collect(Collectors.toSet()), actual);
        verify(quizResultsRepository, times(1)).findAll();
    }

    @Test
    public void getQuizResultsStatistic_WithQuizResultSearchDto_ThenReturnList() {
        List<QuizResults> quizzes = new ArrayList<>();
        quizzes.add(new QuizResults().setId(1L).setQuiz(new Quiz().setQuizName("name")).setTeam(new Team().setTeamName("name")));
        quizzes.add(new QuizResults().setId(2L).setQuiz(new Quiz().setQuizName("name")).setTeam(new Team().setTeamName("name")));
        quizzes.add(new QuizResults().setId(3L).setQuiz(new Quiz().setQuizName("name")).setTeam(new Team().setTeamName("name")));
        when(quizResultsRepository.findResultsWithCustomQuery(new QuizResultsSearchDto())).thenReturn(quizzes);

        List<QuizResultsDto> actual = quizResultService.getQuizResultsStatistics(new QuizResultsSearchDto());

        Assertions.assertEquals(quizzes.size(), actual.size());
        verify(quizResultsRepository, times(1)).findResultsWithCustomQuery(new QuizResultsSearchDto());
    }

    @Test
    public void createResultTable_WithQuizId_ThenSaveData() { //TODO: correct, fails comparison
        Quiz quiz = new Quiz();
        quiz.setState(State.COMPLETED);
        quiz.setId(1L);

        List<Team> teams = new ArrayList<>();
        teams.add(new Team().setId(1L).setTeamName("name"));
        teams.add(new Team().setId(2L).setTeamName("name1"));
        teams.add(new Team().setId(3L).setTeamName("name3"));
        quiz.setTeams(teams);

        when(quizService.findQuizById(1L)).thenReturn(quiz);

        when(quizResultsRepository.save(new QuizResults())).thenReturn(new QuizResults());

        quizResultService.createResultsTable(1L);
        List<QuizResults> actual = quizResultsRepository.findAll();

        Assertions.assertEquals(3, actual.size());
        verify(quizResultsRepository, times(1)).save(new QuizResults());
    }
}