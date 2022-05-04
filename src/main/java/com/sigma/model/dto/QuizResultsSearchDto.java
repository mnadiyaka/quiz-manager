package com.sigma.model.dto;

import com.sigma.model.entity.Period;
import com.sigma.model.entity.Category;
import lombok.Data;

import java.time.LocalDate;

@Data
public class QuizResultsSearchDto {

    Category category;

    Long locationId;

    LocalDate date;

    Period period;
}
