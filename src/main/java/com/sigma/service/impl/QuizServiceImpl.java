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

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    private final UserService userService;

    @Override
    public List<QuizDto> getAllQuizzes() {
        log.info("Getting list of quiz");
        return quizRepository.findAll().stream().map(quiz -> QuizDto.fromQuiz(quiz)).toList();
    }

    @Override
    public QuizDto findQuizById(Long quizId) {
        log.info("Searching for quiz with id {}", quizId);
        QuizDto quizDto = QuizDto.fromQuiz(quizRepository.findById(quizId).get());
        if (quizDto == null) {
            throw new EntityNotFoundException();
        }

        return quizDto;
    }

    @Override
    @Transactional
    public Quiz createQuiz(QuizDto quiz, Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User with id = " + userId + " not found");
        }

        if (user.getRole().equals(Role.CAPTAIN)) {
            throw new EntityNotFoundException("This user is captain, not admin");
        }

        log.info("Creating new quiz {}", quiz.toString());
        return quizRepository.save(QuizDto.toQuiz(quiz));
    }

    @Override
    @Transactional
    public Quiz updateQuiz(QuizDto updatedQuiz, Long quizId, Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User with id = " + userId + " not found");
        }

        if (user.getRole().equals(Role.CAPTAIN)) {
            throw new EntityNotFoundException("This user is captain, not admin");
        }

        QuizDto oldQuiz = QuizDto.fromQuiz(quizRepository.findById(quizId).get());
        if (oldQuiz == null) {
            throw new EntityNotFoundException();
        }
        log.info("Updating quiz {}", oldQuiz);
        if (updatedQuiz.getQuizName() != null) {
            oldQuiz.setQuizName(updatedQuiz.getQuizName());
        }
        if (updatedQuiz.getCategory() != null) {
            oldQuiz.setCategory(updatedQuiz.getCategory());
        }
        if (updatedQuiz.getDateTime() != null) {
            oldQuiz.setDateTime(updatedQuiz.getDateTime());
        }
        if (updatedQuiz.getShortDescription() != null) {
            oldQuiz.setShortDescription(updatedQuiz.getShortDescription());
        }
        return quizRepository.save(QuizDto.toQuiz(oldQuiz));
    }

    @Override
    @Transactional
    public void deleteQuiz(Long userId, Long quizId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("User with id = " + userId + " not found");
        }

        if (user.getRole().equals(Role.CAPTAIN)) {
            throw new EntityNotFoundException("This user is captain, not admin");
        }

        if (!quizRepository.existsById(quizId)) {
            throw new EntityNotFoundException("No quiz with this id");
        }
        log.info("Deleting quiz {}", quizRepository.getById(quizId));
        quizRepository.deleteById(quizId);
    }
}