package com.sigma.model.dto;

import com.sigma.model.entity.Aggregation;
import com.sigma.model.entity.Grouping;
import lombok.Data;

import java.util.Objects;

@Data
public class AggregationStatisticsDto {
    Aggregation aggregation;

    Grouping grouping;

    boolean team;

    public boolean shouldApplyAggregation(){
        return Objects.nonNull(aggregation)||Objects.nonNull(grouping);
    }
}
