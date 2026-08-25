package com.ddnik.db.dto;

import java.util.Objects;

public record UserRolesDto (
        long id,
        String name
) implements IDto {

    public UserRolesDto {
        Objects.requireNonNull(name);
    }

    public static String getMenuTableHeader() {
        return "№ | Роль";
    }

    @Override
    public String toMenuTableRow() {
        return String.format("%s", name);
    }
}
