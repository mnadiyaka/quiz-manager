package com.sigma.service.impl;

import com.sigma.model.dto.QuizResultsDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.repository.QuizResultsRepository;
import com.sigma.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
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

    @Autowired
    EntityManager em;

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
    public Set<QuizResultsDto> getAllRes() {
        return quizResultsRepository.findAll().stream().map(QuizResultsDto::fromQuizResult).collect(Collectors.toSet());
    }

    @Override
    public Set<QuizResultsDto> filterData(String id, String score) {
        String query = "SELECT * FROM results WHERE (results.team_id = :teamId OR results.total_score > :totalScore)";
        Query q = em.createNativeQuery(query, QuizResults.class);
        q.setParameter("teamId", id);
        q.setParameter("totalScore", score);

        List<QuizResults> res = q.getResultList();

        return res.stream().map(QuizResultsDto::fromQuizResult).collect(Collectors.toSet());
    }

}
