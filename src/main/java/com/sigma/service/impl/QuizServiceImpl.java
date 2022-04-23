package com.sigma.service.impl;

import com.sigma.model.dto.QuizDto;
import com.sigma.model.entity.Quiz;
import com.sigma.repository.QuizRepository;
import com.sigma.service.QuizService;
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
}