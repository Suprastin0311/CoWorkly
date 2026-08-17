package com.ddnik.db.dto;

import com.ddnik.db.IDto;
import com.ddnik.db.entity.Workspaces;

import java.math.BigDecimal;

public class WorkspaceDto implements IDto {
    private final String type;
    private final String name;
    private final int capacity;
    private final BigDecimal hourly_rate;
    private final String status;

    @Override
    public String toMenuRow() {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append(" | ")
                .append(name).append(" | ")
                .append(capacity).append(" | ")
                .append(hourly_rate).append(" | ")
                .append(status);
        return sb.toString();
    }

    public WorkspaceDto(String type, String name, int capacity, BigDecimal hourly_rate, String status) {
        this.type = type;
        this.name = name;
        this.capacity = capacity;
        this.hourly_rate = hourly_rate;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public BigDecimal getHourly_rate() {
        return hourly_rate;
    }

    public String getStatus() {
        return status;
    }

    public Workspaces toWorkspaceEntity() {
        Workspaces entity = new Workspaces(
                null,
                type,

        );
    }
}