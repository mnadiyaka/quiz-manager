package com.sigma.repository;

import com.sigma.model.entity.QuizResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultsRepository extends
        JpaRepository<QuizResults, Long>, CustomQuizResultsStatisticsRepo {

    List<QuizResults> findQuizResultsByQuizId(Long quizId);

}