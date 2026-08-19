package com.ddnik.db.dto;

import com.ddnik.db.IDto;

import java.math.BigDecimal;
import java.util.Objects;

public record WorkspaceAvailableDto (
        long id,
        String typeName,
        String name,
        int minParticipantsCount,
        Integer maxParticipantsCount,
        BigDecimal hourlyRate,
        BigDecimal price
) implements IDto {

    public WorkspaceAvailableDto {
        Objects.requireNonNull(typeName);
        Objects.requireNonNull(name);
        Objects.requireNonNull(maxParticipantsCount);
        Objects.requireNonNull(hourlyRate);
        Objects.requireNonNull(price);
    }

    @Override
    public String toMenuTableRow() {
        return String.format("{} | {} | {} | {} | {} | {}", typeName, name, minParticipantsCount, maxParticipantsCount == 0 ? "<не указано>" : maxParticipantsCount, hourlyRate, price);
    }

    public static String getMenuTableHeader() {
        return "№ | Тип | Название | Минимум человек | Максимум человек | Часовая стоимость | Сумма";
    }
}