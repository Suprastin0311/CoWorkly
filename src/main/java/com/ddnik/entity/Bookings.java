package com.ddnik.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Bookings {
    private final Long id;
    private final long userId;
    private final long workspaceId;
    private final Date startTime;
    private final Date endTime;
    private final int participantsCount;
    private final long statusId;
    private final BigDecimal price;
    private final Date createdAt;

    public Bookings(Long id, long userId, long workspaceId, Date startTime, Date endTime, int participantsCount, long statusId, BigDecimal price, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participantsCount = participantsCount;
        this.statusId = statusId;
        this.price = price;
        this.createdAt = createdAt;
    }

    public long getUserId() {
        return userId;
    }

    public long getWorkspaceId() {
        return workspaceId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public int getParticipantsCount() {
        return participantsCount;
    }

    public long getStatusId() {
        return statusId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
