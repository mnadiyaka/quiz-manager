package com.sigma.repository.impl;

import com.sigma.model.dto.FilterDto;
import com.sigma.model.entity.QuizResults;
import com.sigma.repository.CustomRepo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomRepoImpl implements CustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<QuizResults> findResultsWithCustomQuery(FilterDto data) {

        String query = "SELECT * FROM full_res WHERE (address_id = :locId AND category = :cat AND (datetime BETWEEN :dt AND :dt + :p));";

        final Query q = entityManager.createNativeQuery(query, QuizResults.class);
        q.setParameter("locId", data.getLocationId());
        q.setParameter("cat", data.getCategory());
        q.setParameter("dt", data.getDateTime());
        q.setParameter("p", data.getPeriod());

        List<QuizResults> userList = q.getResultList();

        return userList;
    }
}