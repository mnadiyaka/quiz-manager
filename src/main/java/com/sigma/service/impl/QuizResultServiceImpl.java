package com.sigma.service.impl;

import com.sigma.model.dto.QuizResultsSearchDto;
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
import java.util.Set;
import java.util.stream.Collectors;

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

}