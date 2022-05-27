package com.sigma.repository.impl;

import com.sigma.model.dto.AggregationStatisticResDto;
import com.sigma.model.dto.AggregationStatisticsDto;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.repository.CustomQuizResultsStatisticsRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomQuizResultsStatisticsRepoImpl implements CustomQuizResultsStatisticsRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuizResults> findResultsWithCustomQuery(QuizResultsSearchDto data) {

        String query = "SELECT r.id, r.quiz_id, r.team_id, r.score " +
                "FROM quiz_results r JOIN quizzes q on r.quiz_id = q.quiz_id ";
        List<String> params = new ArrayList<>();
        if (data.shouldApplyFilters()) {
            query += "WHERE ";
            Optional.ofNullable(data.getLocationId()).ifPresent((el) -> params.add("address_id = " + el));
            Optional.ofNullable(data.getCategory()).ifPresent((el) -> params.add("category = " + el.ordinal()));
            Optional.ofNullable(data.getDate()).ifPresent(date -> {
                params.add("datetime >= '" + date + "'");
                Optional.ofNullable(data.getPeriod()).ifPresent((el) -> params.add("datetime <= '" + date.plusDays(el.getDays()) + "'"));
            });
        }
        query += params.stream().collect(Collectors.joining(" AND ")) + ";";
        final Query q = entityManager.createNativeQuery(query, QuizResults.class);

        return q.getResultList();
    }

    @Override
    public List<Object[]> findQuizResultAggregatedData(AggregationStatisticsDto data) {
        String query;
        if (data.shouldApplyAggregation()) {
            query = "SELECT q." + data.getGrouping().getParam() + ", " + data.getAggregation() + "(r.score)" +
                    " FROM quiz_results r JOIN quizzes q on r.quiz_id = q.quiz_id " +
                    " GROUP BY q." + data.getGrouping().getParam() + ";";
        } else {
            query = "SELECT r.id, r.quiz_id, r.team_id, r.score " +
                    " FROM quiz_results r JOIN quizzes q on r.quiz_id = q.quiz_id;";
        }
        final Query q = entityManager.createNativeQuery(query);

        List<Object[]> list = q.getResultList();
        return list;
    }
}