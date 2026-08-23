package com.ddnik.db.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public record BookingDto (
        long id,
        long workspaceId,
        long wTypeId,
        long userId,
        String userEmail,
        String userFullName,
        String workspaceTypeName,
        String workspaceName,
        Date startTime,
        Date endTime,
        int participantsCount,
        String status,
        BigDecimal price,
        Date createdAt
) implements IDto {

    public BookingDto {
        Objects.requireNonNull(userEmail);
        Objects.requireNonNull(userFullName);
        Objects.requireNonNull(workspaceTypeName);
        Objects.requireNonNull(workspaceName);
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
        Objects.requireNonNull(status);
        Objects.requireNonNull(price);
        Objects.requireNonNull(createdAt);
    }

    @Override
    public String toMenuTableRow() {
        return String.format("%s | %s | %tF%<tR | %tF%<tR | %s | %d | %s | %,3.2f | %tF%<tR", workspaceTypeName, workspaceName, startTime, endTime, userEmail, participantsCount, status, price, createdAt);
    }

    public static String getMenuTableHeader() {
        return "№ | Тип | Название | Время начала | Время окончания | Email регистратора | Количество человек | Статус | Сумма | Дата брони";
    }
}
