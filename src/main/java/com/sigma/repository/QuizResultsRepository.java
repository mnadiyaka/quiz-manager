package com.sigma.repository;

import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;
import com.sigma.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuizResultsRepository extends JpaRepository<QuizResults, Long> {

    public QuizResults findQuizResultsByTeamAndQuiz(Team team, Quiz quiz);
}