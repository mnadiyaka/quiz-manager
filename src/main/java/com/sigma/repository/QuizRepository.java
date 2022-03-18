package com.sigma.repository;

import com.sigma.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository {
    public List<Quiz> getAllQuizzes();
}
