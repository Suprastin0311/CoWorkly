package com.ddnik.db.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class BookingDto implements IDTO {
    private final String workspaceType;
    private final String workspaceName;
    private final Date startTime;
    private final Date endTime;
    private final int participantsCount;
    private final String status;
    private final BigDecimal price;
    private final Date createdAt;

    @Override
    public String toMenuRow() {
        StringBuilder sb = new StringBuilder();
        sb.append(workspaceType).append(" | ")
                .append(workspaceName).append(" | ")
                .append(startTime).append(" | ")
                .append(endTime).append(" | ")
                .append(participantsCount).append(" | ")
                .append(status).append(" | ")
                .append(price).append(" | ")
                .append(createdAt);
        return sb.toString();
    }

    public BookingDto(String workspaceType, String workspaceName, Date startTime, Date endTime, int participantsCount, String status, BigDecimal price, Date createdAt) {
        this.workspaceType = workspaceType;
        this.workspaceName = workspaceName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participantsCount = participantsCount;
        this.status = status;
        this.price = price;
        this.createdAt = createdAt;
    }
}
