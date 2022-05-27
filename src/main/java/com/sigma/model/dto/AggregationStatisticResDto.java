package com.sigma.model.dto;

import com.sigma.model.entity.Grouping;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Accessors(chain = true)
@Data
public class AggregationStatisticResDto {

    Integer data;

    BigDecimal res;

    public static AggregationStatisticResDto toDto(Object[] obj){
        return new AggregationStatisticResDto().setData((Integer) obj[0]).setRes((BigDecimal) obj[1]);
    }
}
