package com.sigma.repository.impl;

import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.repository.CustomQuizResultsStatisticsRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomQuizResultsStatisticsRepoImpl implements CustomQuizResultsStatisticsRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuizResults> findResultsWithCustomQuery(QuizResultsSearchDto data) {

        final StringBuilder query = new StringBuilder("SELECT r.id, r.quiz_id, r.team_id, r.score " +
                "FROM quiz_results r JOIN quizzes q on r.quiz_id = q.quiz_id ");
        Optional.ofNullable(data).ifPresent(data1-> {
            query.append("WHERE");
            Optional.ofNullable(data1.getLocationId()).ifPresent((el) -> query.append(" AND address_id = " + el));
            Optional.ofNullable(data1.getCategory()).ifPresent((el) -> query.append(" AND category = " + el.ordinal()));
            Optional.ofNullable(data1.getDate()).ifPresent(date -> {
                query.append(" AND datetime >= '" + date + "'");
                Optional.ofNullable(data1.getPeriod()).ifPresent((el) -> query.append(" AND datetime <= '" + date.plusDays(el.getDays()) + "'"));
            });
        });
        query.append(";");

        final Query q = entityManager.createNativeQuery(query.toString().replace("WHERE AND", "WHERE").replace("WHERE;", ";"), QuizResults.class);

        List<QuizResults> userList = q.getResultList();

        return userList;
    }
}