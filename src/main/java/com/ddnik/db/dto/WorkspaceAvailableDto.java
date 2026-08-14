package com.ddnik.db.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class WorkspaceAvailableDto implements IDto {
    private final long id;
    private final String type;
    private final String name;
    private final Integer minParticipantsCount;
    private final Integer maxParticipantsCount;
    private final BigDecimal hourly_rate;
    private final BigDecimal price;

    @Override
    public String toMenuRow() {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append(" | ")
                .append(name).append(" | ")
                .append(minParticipantsCount).append(" | ")
                .append(maxParticipantsCount).append(" | ")
                .append(hourly_rate).append(" | ")
                .append(price);
        return sb.toString();
    }

    public WorkspaceAvailableDto(long id, String type, String name, Integer minParticipantsCount, Integer maxParticipantsCount, BigDecimal hourly_rate, BigDecimal price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.minParticipantsCount = minParticipantsCount;
        this.maxParticipantsCount = maxParticipantsCount;
        this.hourly_rate = hourly_rate;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getMinParticipantsCount() {
        return minParticipantsCount;
    }

    public Integer getMaxParticipantsCount() {
        return maxParticipantsCount;
    }

    public BigDecimal getHourly_rate() {
        return hourly_rate;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
