package com.ddnik.db.dto;

import java.math.BigDecimal;
import java.sql.Date;

public record BookingDto(
        String workspaceType,
        String workspaceName,
        Date startTime,
        Date endTime,
        int participantsCount,
        String status,
        BigDecimal price,
        Date createdAt
) {}
