package com.sigma.service.impl;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Location;
import com.sigma.model.entity.Quiz;
import com.sigma.exception.QuizException;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Location;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.State;
import com.sigma.model.entity.Team;
import com.sigma.repository.QuizRepository;
import com.sigma.service.LocationService;
import com.sigma.service.QuizService;
import com.sigma.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    private final TeamService teamService;

    private final LocationService locationService;

    @Override
    public List<QuizDto> getAllQuizzes() {
        log.info("Getting list of quiz");
        return quizRepository.findAll().stream().map(QuizDto::fromQuiz).toList();
    }

    @Override
    public Quiz findQuizById(final Long quizId) {
        log.info("Searching for quiz with id {}", quizId);
        return quizRepository.findById(quizId).orElseThrow(() -> new EntityNotFoundException("Quiz with id = " + quizId + " not found"));
    }

    @Override
    @Transactional
    public Quiz createQuiz(final QuizDto quiz) {
        log.info("Creating new quiz {}", quiz.toString());
        return quizRepository.save(QuizDto.toQuiz(quiz));
    }

    @Override
    @Transactional
    public Quiz updateQuiz(final QuizDto updatedQuiz, final Long quizId) {

        final Quiz oldQuiz = findQuizById(quizId);

        log.info("Updating quiz {}", oldQuiz);
        Optional.ofNullable(updatedQuiz.getQuizName()).ifPresent(oldQuiz::setQuizName);
        Optional.ofNullable(updatedQuiz.getCategory()).ifPresent(oldQuiz::setCategory);
        Optional.ofNullable(updatedQuiz.getDateTime()).ifPresent(oldQuiz::setDateTime);
        Optional.ofNullable(updatedQuiz.getShortDescription()).ifPresent(oldQuiz::setShortDescription);
        Optional.ofNullable(updatedQuiz.getAddressId()).ifPresent(oldQuiz::setAddressId);

        return quizRepository.save(oldQuiz);
    }

    @Override
    @Transactional
    public void deleteQuiz(final Long quizId) {
        log.info("Deleting quiz {}", quizId);
        quizRepository.delete(findQuizById(quizId));
    }

    @Override
    @Transactional
    public void applyForQuiz(final Long quizId, final Long teamId) {
        Quiz quiz = findQuizById(quizId);
        List<Team> teams = quiz.getTeams();

        if (!quiz.getState().equals(State.ANOUNCED)) {
            throw new QuizException("Quiz closed, try another one");
        }

        if (teams.size() > quiz.getTeamNumberMax()) {
            quiz.setState(State.CLOSED);
            updateQuiz(QuizDto.fromQuiz(quiz), quizId);
            throw new QuizException("Quiz closed, try another one");
        }

        Team team = teamService.applyForQuiz(quiz, teamId);
        teams.add(team);
        quiz.setTeams(teams);
        quizRepository.save(quiz);
    }


    @Override
    @Transactional
    public void changeQuizState(Long quizId, String state) {
        Quiz quiz = findQuizById(quizId);
        quiz.setState(State.valueOf(state));
        quizRepository.save(quiz);
    }

    @Override
    @Transactional
    public Quiz assignLocation(final Long quizId, final Long locId){
        Quiz quiz = findQuizById(quizId);
        Location location = locationService.findLocationById(locId);
        quiz.setAddressId(locId);

        return quizRepository.save(quiz);
    }
}