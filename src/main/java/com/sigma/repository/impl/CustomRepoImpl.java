package com.sigma.repository.impl;

import com.sigma.model.dto.FilterDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.repository.CustomRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class CustomRepoImpl implements CustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuizResults> findResultsWithCustomQuery(FilterDto data) {

        String query = "SELECT * FROM results WHERE (";

        String field = "";
        for (String key : param.keySet()) {
            field="";
            for (int j = 0; j < key.length(); j++) {
                if (Character.isUpperCase(key.charAt(j))) {
                    field += key.substring(0, j) + "_" + key.substring(j, j + 1).toLowerCase() + key.substring(j + 1);
                    break;
                }
            }

            query += "results." + field + " = " +param.get(key) + " OR ";
        }

        query = query.substring(0, query.length() - 4) + ");";

        final Query q = entityManager.createNativeQuery(query, QuizResults.class);
        List<QuizResults> userList = q.getResultList();

        return userList;
    }
}