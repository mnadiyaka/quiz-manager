package com.sigma.service.impl;

import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.repository.QuizResultsRepository;
import com.sigma.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizResultServiceImpl implements QuizResultService {

    private final QuizResultsRepository quizResultsRepository;

    @Override
    public QuizResults findResById(Long id) {
        return quizResultsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No record with this id"));
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

        Optional.ofNullable(newQuizResults.getTotalScore()).ifPresent(quizResults::setTotalScore);

        return quizResultsRepository.save(quizResults);
    }

    @Override
    @Transactional
    public void deleteRes(Long id) {
        quizResultsRepository.delete(findResById(id));
    }

    @Override
    public List<QuizResults> getAllRes() {
        return quizResultsRepository.findAll();
    }

}
