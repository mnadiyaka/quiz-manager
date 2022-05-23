package com.sigma.model.dto;

import com.sigma.model.entity.Period;
import com.sigma.model.entity.Category;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class QuizResultsSearchDto {

    Category category;

    Long locationId;

    LocalDate date;

    Period period;

    public boolean shouldApplyFilters(){
        return Objects.nonNull(category)||Objects.nonNull(locationId)||Objects.nonNull(date)||Objects.nonNull(period);
    }

}
