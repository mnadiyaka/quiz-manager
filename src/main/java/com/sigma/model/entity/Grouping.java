package com.sigma.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grouping {
    PERIOD("period"), CATEGORY_NAME("category"), DATE("datetime"), LOCATION_NAME("address_id");

    final String param;
}
