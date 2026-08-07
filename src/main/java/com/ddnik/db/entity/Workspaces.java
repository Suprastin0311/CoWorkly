package com.ddnik.db.entity;

import java.math.BigDecimal;

public class Workspaces {
    private final Long id;
    private final WorkspaceTypes type;
    private final String name;
    private final int capacity;
    private final BigDecimal hourlyRate;
    private final boolean isActive;

    public Workspaces(Long id, WorkspaceTypes type, String name, int capacity, BigDecimal hourlyRate, boolean isActive) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.capacity = capacity;
        this.hourlyRate = hourlyRate;
        this.isActive = isActive;
    }

    public Workspaces(boolean isActive, BigDecimal hourlyRate, int capacity, String name, WorkspaceTypes type) {
        this.id = null;
        this.isActive = isActive;
        this.hourlyRate = hourlyRate;
        this.capacity = capacity;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public WorkspaceTypes getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public boolean isActive() {
        return isActive;
    }
}
