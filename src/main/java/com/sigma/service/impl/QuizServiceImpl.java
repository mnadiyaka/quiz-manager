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

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

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
    public Quiz createQuiz(QuizDto quiz) {
        log.info("Creating new quiz {}", quiz.toString());
        return quizRepository.save(QuizDto.toQuiz(quiz));
    }

    @Override
    @Transactional
    public void updateQuiz(QuizDto updatedQuiz, Long quizId) {
        QuizDto oldQuiz = QuizDto.fromQuiz(quizRepository.findById(quizId).get());
        if (oldQuiz == null) {
            throw new EntityNotFoundException();
        }
        log.info("Updating quiz {}", oldQuiz);
        oldQuiz.setQuizName(updatedQuiz.getQuizName());
        oldQuiz.setCategory(updatedQuiz.getCategory());
        oldQuiz.setDateTime(updatedQuiz.getDateTime());
        oldQuiz.setShortDescription(updatedQuiz.getShortDescription());

        quizRepository.save(QuizDto.toQuiz(oldQuiz));
    }

    @Override
    @Transactional
    public void deleteQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new EntityNotFoundException();
        }
        log.info("Deleting quiz {}", quizRepository.getById(quizId));
        quizRepository.deleteById(quizId);
    }
}