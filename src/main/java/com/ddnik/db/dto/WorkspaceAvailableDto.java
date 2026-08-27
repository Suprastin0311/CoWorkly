package com.ddnik.db.dto;

import java.math.BigDecimal;
import java.util.Objects;

public record WorkspaceAvailableDto (
        long id,
        WorkspaceTypesDto type,
        String name,
        int capacity,
        BigDecimal hourlyRate,
        BigDecimal price
) implements IDto {

    public WorkspaceAvailableDto {
        Objects.requireNonNull(name);
        Objects.requireNonNull(type);
        Objects.requireNonNull(hourlyRate);
        Objects.requireNonNull(price);
    }

    @Override
    public String toMenuTableRow() {
        return String.format("%s | %s | %d | %s | %,3.2f | %,3.2f", type.nameRus(), name, type.minParticipantsCount(),
                type.maxParticipantsCount() == 0 ? "<не указано>" : type.maxParticipantsCount(), hourlyRate, price);
    }

    public static String getMenuTableHeader() {
        return "№ | Тип | Название | Минимум человек | Максимум человек | Часовая стоимость | Сумма";
    }

    public WorkspaceDto toWorkspaceDto() {
        return new WorkspaceDto(id, type, name, capacity, hourlyRate, true, "Активно");
    }
}