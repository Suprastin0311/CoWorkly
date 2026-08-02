package com.ddnik.db.dto;

import java.math.BigDecimal;

public record WorkspaceDto (
        String type,
        String name,
        int capacity,
        BigDecimal hourly_rate,
        String status
) {}