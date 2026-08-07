package com.ddnik.db.dto;

import java.math.BigDecimal;
import java.util.Objects;

public record WorkspaceAvailableDto(
        long id,
        String type,
        String name,
        Integer minParticipantsCount,
        Integer maxParticipantsCount,
        BigDecimal hourly_rate,
        BigDecimal price
) {

    public WorkspaceAvailableDto {
            Objects.requireNonNull(type);
            Objects.requireNonNull(name);
            Objects.requireNonNull(minParticipantsCount);
            Objects.requireNonNull(hourly_rate);
            Objects.requireNonNull(price);
    }
}
