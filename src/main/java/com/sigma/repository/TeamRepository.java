package com.sigma.repository;

import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    public List<Team> findTeamsByQuizzesContaining(Quiz quiz);
}