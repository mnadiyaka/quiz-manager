package com.sigma.model.entity;

import lombok.Getter;

@Getter
public enum Period {
    WEEK(7),
    MONTH(30),
    QUARTER(90),
    YEAR(360);

    private final int days;

    Period(int days) {
        this.days = days;
    }
}