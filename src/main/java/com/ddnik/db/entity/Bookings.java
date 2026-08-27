package com.ddnik.db.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public record Bookings(
        Long id,
        long userId,
        long workspaceId,
        Timestamp startTime,
        Timestamp endTime,
        int participantsCount,
        long statusId,
        BigDecimal price,
        Timestamp createdAt
) {

    public Bookings {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(workspaceId);
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
        Objects.requireNonNull(participantsCount);
        Objects.requireNonNull(statusId);
        Objects.requireNonNull(price);
        Objects.requireNonNull(createdAt);
    }

    public Bookings (long userId, long workspaceId, Timestamp startTime, Timestamp endTime, int participantsCount, long statusId, BigDecimal price, Timestamp createdAt) {
        this(null, userId, workspaceId, startTime, endTime, participantsCount, statusId, price, createdAt);
    }
}