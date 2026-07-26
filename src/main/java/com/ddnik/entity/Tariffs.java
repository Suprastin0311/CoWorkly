package com.ddnik.entity;

import java.math.BigDecimal;

public class Tariffs {
    private final Long id;
    private final long workspaceType;
    private final long dayType;
    private final BigDecimal multiplier;

    public Tariffs(Long id, long workspaceType, long dayType, BigDecimal multiplier) {
        this.id = id;
        this.workspaceType = workspaceType;
        this.dayType = dayType;
        this.multiplier = multiplier;
    }

    public Long getId() {
        return id;
    }

    public long getWorkspaceType() {
        return workspaceType;
    }

    public long getDayType() {
        return dayType;
    }

    public BigDecimal getMultiplier() {
        return multiplier;
    }
}
