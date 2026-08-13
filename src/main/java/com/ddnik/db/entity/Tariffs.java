package com.ddnik.db.entity;

import java.math.BigDecimal;
import java.util.Objects;

public record Tariffs (
        Long id,
        long workspaceType,
        long dayType,
        BigDecimal multiplier
) {
    public Tariffs {
        Objects.requireNonNull(workspaceType);
        Objects.requireNonNull(dayType);
        Objects.requireNonNull(multiplier);
    }

    public Tariffs(long workspaceType, long dayType, BigDecimal multiplier) {
        this(null, workspaceType, dayType, multiplier);
    }

}
