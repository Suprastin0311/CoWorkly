package com.ddnik.db.dto;

import com.ddnik.db.IDto;

import java.math.BigDecimal;
import java.util.Objects;

public record WorkspaceDto (
        long id,
        WorkspaceTypesDto type,
        String name,
        int capacity,
        BigDecimal hourlyRate,
        String status
) implements IDto  {

    public WorkspaceDto {
        Objects.requireNonNull(name);
        Objects.requireNonNull(hourlyRate);
        Objects.requireNonNull(status);
        Objects.requireNonNull(type);
    }

    @Override
    public String toMenuTableRow() {
        return String.format("%s | %s | %d | %,3.2f | %s", type.nameRus(), name, capacity, hourlyRate, status);
    }

    public static String getMenuTableHeader() {
        return "№ | Тип | Название | Вместимость | Цена за час | Статус";
    }
}