package com.sigma.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grouping {
    PERIOD("period"), CATEGORY_NAME("category"), DATE("date"), LOCATION_NAME("locationName");

    String param;
}
