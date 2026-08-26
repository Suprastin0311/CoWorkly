package com.ddnik.db.dto;

import java.util.Objects;

public record BookingStatusesDto (
        long id,
        String name
) implements IDto {

    public BookingStatusesDto {
        Objects.requireNonNull(name);
    }

    public static String getMenuTableHeader() {
        return "№ | Статус";
    }

    public String toMenuTableRow() {
        return String.format("%s", name);
    }
}
