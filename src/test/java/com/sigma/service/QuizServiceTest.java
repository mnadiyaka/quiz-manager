package com.sigma.service;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Location;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.State;
import com.sigma.repository.LocationRepository;
import com.sigma.repository.QuizRepository;
import com.sigma.repository.TeamRepository;
import com.sigma.service.impl.LocationServiceImpl;
import com.sigma.service.impl.QuizServiceImpl;
import com.sigma.service.impl.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class QuizServiceTest {

    private QuizRepository quizRepository;

    private QuizService quizService;

    private LocationRepository locationRepository;

    private LocationService locationService;

    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        locationRepository = mock(LocationRepository.class);
        locationService = mock(LocationServiceImpl.class);

        teamRepository  = mock(TeamRepository.class);
        teamService = mock(TeamServiceImpl.class);

        quizRepository = mock(QuizRepository.class);
        quizService = new QuizServiceImpl(quizRepository, teamService, locationService);
    }

    @Test
    public void findQuizById_WithExistingId_ThenReturnQuiz() {
        final Quiz expectedResult = new Quiz();
        when(quizRepository.findById(anyLong())).thenReturn(Optional.of(expectedResult));
        final Quiz result = quizService.findQuizById(anyLong());

        Assertions.assertEquals(expectedResult, result);
        verify(quizRepository, times(1)).findById(anyLong());
    }

    @Test
    public void findQuizById_WithNonExistingId_ThenThrowException() {
        when(quizRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> quizService.findQuizById(anyLong()));
        verify(quizRepository, times(1)).findById(anyLong());
    }

    @Test
    public void createQuiz_WithLocationDto_ThenReturnNewQuiz() {
        Quiz expected = new Quiz();

        when(quizRepository.save(expected)).thenReturn(expected);

        Quiz actual = quizService.createQuiz(QuizDto.fromQuiz(expected));
        Assertions.assertEquals(expected, actual);
        verify(quizRepository, times(1)).save(expected);
    }

    @Test
    public void updateQuiz_WithQuizDtoAndId_ThenReturnUpdatedQuiz() {
        Quiz quiz = new Quiz();
        quiz.setId(1L);
        when(quizRepository.findById(anyLong())).thenReturn(Optional.of(quiz));

        Quiz oldQuiz = quizService.findQuizById(anyLong());
        oldQuiz.setQuizName("new");

        when(quizRepository.save(oldQuiz)).thenReturn(oldQuiz);

        Quiz actual = quizService.updateQuiz(QuizDto.fromQuiz(quiz), quiz.getId());
        Assertions.assertEquals(oldQuiz, actual);
        verify(quizRepository, times(1)).save(quiz);
    }

    @Test
    public void deleteQuiz_WithQuizId() {
        Quiz expected = new Quiz();
        expected.setId(1L);
        when(quizRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        doNothing().when(quizRepository).delete(any());

        quizService.deleteQuiz(expected.getId());
        verify(quizRepository).delete(eq(expected));
    }

    @Test
    public void getAllQuizzes_ThenReturnList() {
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(new Quiz());
        quizzes.add(new Quiz());
        quizzes.add(new Quiz());
        when(quizRepository.findAll()).thenReturn(quizzes);

        List<QuizDto> actual = quizService.getAllQuizzes();
        Assertions.assertEquals(quizzes.stream().map(QuizDto::fromQuiz).collect(Collectors.toList()), actual);
        verify(quizRepository, times(1)).findAll();
    }

    //TODO assign

    @Test
    public void changeQuizState_WithQuizIdAndNewState_ThenSaveQuiz(){
        Quiz expected = new Quiz();
        expected.setId(1L);
        expected.setState(State.ANOUNCED);

        when(quizRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(quizRepository.save(expected)).thenReturn(expected);

        quizService.changeQuizState(1L, State.CLOSED.toString());
        Assertions.assertEquals(State.CLOSED, quizService.findQuizById(1L).getState());
        verify(quizRepository, times(1)).save(expected);
    }

    @Test
    public void assignLocation_WithQuizIdAndLocId_ThenSaveQuiz(){
        Quiz expected = new Quiz();
        expected.setId(1L);

        Location location = new Location();
        location.setId(1L);

        when(quizRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(quizRepository.save(expected)).thenReturn(expected);

        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));

        quizService.assignLocation(1L, 1L);
        Assertions.assertEquals(1L, quizService.findQuizById(1L).getAddressId());
        verify(quizRepository, times(1)).save(expected);
    }
}