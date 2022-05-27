package com.sigma.repository.impl;

import com.sigma.model.dto.AggregationStatisticsDto;
import com.sigma.model.dto.QuizResultsSearchDto;
import com.sigma.model.entity.Quiz;
import com.sigma.model.entity.QuizResults;
import com.sigma.model.entity.State;
import com.sigma.repository.CustomQuizResultsStatisticsRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
    @Transactional
    public List<QuizResults> findQuizResultAggregatedData(AggregationStatisticsDto data) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/quiz_manager", "Admin", "Admin");
        Statement statement = connection.createStatement();

        String query ;/* "DELETE FROM aggregation_result WHERE TRUE; " +
                "INSERT INTO aggregation_result (id, quiz_id, team_id, score, result) ";
*/        if (data.shouldApplyAggregation()) {
            query = "SELECT r.id, r.quiz_id, r.team_id, r.score, " + data.getAggregation() + "(r.score)" +
                    " FROM quiz_results r JOIN quizzes q on r.quiz_id = q.quiz_id " +
                    " GROUP BY q." + data.getGrouping().getParam() + ";";
        } else {
            query = "SELECT r.id, r.quiz_id, r.team_id, r.score " +
                    " FROM quiz_results r JOIN quizzes q on r.quiz_id = q.quiz_id;";
        }
        //query+= "SELECT ar.id, ar.quiz_id, ar.team_id, ar.score, ar.result FROM aggregation_result ar" ;
        final Query q = entityManager.createNativeQuery(query, QuizResults.class);

        //statement.executeUpdate(query);
        return q.getResultList();
    }
}