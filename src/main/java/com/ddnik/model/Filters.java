package com.ddnik.model;

import com.ddnik.db.dto.WorkspaceTypesDto;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * Хранит данные для фильтрации рабочих пространств.
 */
public record Filters(
        WorkspaceTypesDto type,
        int participantsCount,
        Timestamp startTime,
        Timestamp endTime
) {
    public Filters {
        Objects.requireNonNull(type);
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
    }
}
