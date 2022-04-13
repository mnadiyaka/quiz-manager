package com.sigma.service.impl;

import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Role;
import com.sigma.model.entity.User;
import com.sigma.repository.QuizRepository;
import com.sigma.service.QuizService;
import com.sigma.service.UserService;
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

    private final UserService userService;

    @Override
    public List<QuizDto> getAllQuizzes() {
        log.info("Getting list of quiz");
        return quizRepository.findAll().stream().map(QuizDto::fromQuiz).toList();
    }

    @Override
    public QuizDto findQuizById(Long quizId) {
        log.info("Searching for quiz with id {}", quizId);
        return QuizDto.fromQuiz(quizRepository.findById(quizId).orElseThrow(() -> new EntityNotFoundException("Quiz with id = " + quizId + " not found")));
    }

    @Override
    @Transactional
    public Quiz createQuiz(QuizDto quiz, Long userId) {
        User user = checkUser(userId);

        log.info("Creating new quiz {}", quiz.toString());
        return quizRepository.save(QuizDto.toQuiz(quiz));
    }

    @Override
    @Transactional
    public Quiz updateQuiz(QuizDto updatedQuiz, Long quizId, Long userId) {
        User user = checkUser(userId);

        QuizDto oldQuiz = findQuizById(quizId);

        log.info("Updating quiz {}", oldQuiz);
        Optional.ofNullable(updatedQuiz.getQuizName()).ifPresent(oldQuiz::setQuizName);
        Optional.ofNullable(updatedQuiz.getCategory()).ifPresent(oldQuiz::setCategory);
        Optional.ofNullable(updatedQuiz.getDateTime()).ifPresent(oldQuiz::setDateTime);
        Optional.ofNullable(updatedQuiz.getShortDescription()).ifPresent(oldQuiz::setShortDescription);

        return quizRepository.save(QuizDto.toQuiz(oldQuiz));
    }

    @Override
    @Transactional
    public void deleteQuiz(Long userId, Long quizId) {
        User user = checkUser(userId);

        log.info("Deleting quiz {}", findQuizById(quizId));
        quizRepository.deleteById(quizId);
    }

    private User checkUser(Long userId) {
        User user = userService.findUserById(userId);

        if (user.getRole().equals(Role.CAPTAIN)) {
            throw new EntityNotFoundException("This user is captain, not admin");
        }
        return user;
    }
}