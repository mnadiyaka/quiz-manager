package com.sigma.model.dto;

import com.sigma.model.Period;
import com.sigma.model.entity.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FilterDto {

    Category category;

    Long locationId;

    LocalDateTime dateTime;

    Period period;
}
