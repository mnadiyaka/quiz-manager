package com.sigma.service.impl;

import com.sigma.model.dto.LocationDto;
import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Location;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Role;
import com.sigma.model.entity.User;
import com.sigma.repository.QuizRepository;
import com.sigma.service.LocationService;
import com.sigma.service.QuizService;
import com.sigma.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    private final UserService userService;

    private final LocationService locationService;

    @Override
    public Set<QuizDto> getAllQuizzes() {
        log.info("Getting list of quiz");
        return quizRepository.findAll().stream().map(QuizDto::fromQuiz).collect(Collectors.toSet());
    }

    @Override
    public Quiz findQuizById(final Long quizId) {
        log.info("Searching for quiz with id {}", quizId);
        return quizRepository.findById(quizId).orElseThrow(() -> new EntityNotFoundException("Quiz with id = " + quizId + " not found"));
    }

    @Override
    @Transactional
    public Quiz createQuiz(final QuizDto quiz, final Long userId) {
        final User user = checkUser(userId);

        log.info("Creating new quiz {}", quiz.toString());
        return quizRepository.save(QuizDto.toQuiz(quiz));
    }

    @Override
    @Transactional
    public Quiz updateQuiz(final QuizDto updatedQuiz, final Long quizId, final Long userId) {
        final User user = checkUser(userId);

        final Quiz oldQuiz = findQuizById(quizId);

        log.info("Updating quiz {}", oldQuiz);
        Optional.ofNullable(updatedQuiz.getQuizName()).ifPresent(oldQuiz::setQuizName);
        Optional.ofNullable(updatedQuiz.getCategory()).ifPresent(oldQuiz::setCategory);
        Optional.ofNullable(updatedQuiz.getDateTime()).ifPresent(oldQuiz::setDateTime);
        Optional.ofNullable(updatedQuiz.getState()).ifPresent(oldQuiz::setState);
        Optional.ofNullable(updatedQuiz.getShortDescription()).ifPresent(oldQuiz::setShortDescription);

        return quizRepository.save(oldQuiz);
    }

    @Override
    @Transactional
    public void deleteQuiz(final Long userId, final Long quizId) {
        final User user = checkUser(userId);

        log.info("Deleting quiz {}", findQuizById(quizId));
        quizRepository.deleteById(quizId);
    }

    private User checkUser(final Long userId) {
        final User user = userService.findUserById(userId);

        if (user.getRole().equals(Role.CAPTAIN)) {
            throw new EntityNotFoundException("This user is captain, not admin");
        }
        return user;
    }

    @Override
    @Transactional
    public Quiz assignLocation(final Long quizId, LocationDto locationDto){
        Quiz quiz = findQuizById(quizId);
        Location location = locationService.findLocationById(locationDto.getId());
//        if (Objects.equals(location, null)){
//            locationService.createLocation(locationDto,)//TODO: change logic with checking user every time
//        }
        quiz.setAddress(locationService.findLocationById(location.getId()));

        return quizRepository.save(quiz);
    }
}