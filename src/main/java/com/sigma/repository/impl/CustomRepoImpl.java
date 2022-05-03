package com.sigma.repository.impl;

import com.sigma.model.dto.FilterDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.repository.CustomRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomRepoImpl implements CustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuizResults> findResultsWithCustomQuery(FilterDto data) {

        final StringBuilder query = new StringBuilder("SELECT r.id, r.quiz_id, r.team_id, r.total_score " +
                "FROM results r JOIN quizzes q on r.quiz_id = q.quiz_id ");
        if (Optional.ofNullable(data).isPresent()) {
            query.append("WHERE");
            Optional.ofNullable(data.getLocationId()).ifPresent((el) -> query.append(" AND address_id = " + el));
            Optional.ofNullable(data.getCategory()).ifPresent((el) -> query.append(" AND category = " + el.ordinal()));
            if (Optional.ofNullable(data.getDateTime()).isPresent()) {
                query.append(" AND datetime >= " + data.getDateTime());
                Optional.ofNullable(data.getPeriod()).ifPresent((el) -> query.append(" AND datetime <= " + data.getDateTime().plusDays(el.getDays())));
            }
        }
        query.append(";");

        final Query q = entityManager.createNativeQuery(query.toString().replace("WHERE AND", "WHERE").replace("WHERE;", ";"), QuizResults.class);

        List<QuizResults> userList = q.getResultList();

        return userList;
    }
}