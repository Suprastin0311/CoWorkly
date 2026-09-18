package com.ddnik.db.entity;

import java.math.BigDecimal;
import java.util.Objects;

public record Workspaces (
        Long id,
        long type,
        String name,
        int capacity,
        BigDecimal hourlyRate,
        boolean isActive
) {

    public Workspaces {
        Objects.requireNonNull(type);
        Objects.requireNonNull(name);
        Objects.requireNonNull(capacity);
        Objects.requireNonNull(hourlyRate);
        Objects.requireNonNull(isActive);
    }

    public Workspaces (long type, String name, int capacity, BigDecimal hourlyRate, boolean isActive) {
        this(null, type, name, capacity, hourlyRate, isActive);
    }

}