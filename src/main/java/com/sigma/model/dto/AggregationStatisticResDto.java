package com.sigma.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.math.BigInteger;

@Accessors(chain = true)
@Data
public class AggregationStatisticResDto {

    Integer data;

    BigDecimal res;

    BigInteger teamId;

    public static AggregationStatisticResDto toDto(Object[] obj) {
        return new AggregationStatisticResDto().setData((Integer) obj[0]).setRes((BigDecimal) obj[1]).setTeamId(obj.length>2?(BigInteger) obj[2]: BigInteger.valueOf(0));
    }
}
