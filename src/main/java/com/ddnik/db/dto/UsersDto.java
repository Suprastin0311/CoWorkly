package com.ddnik.db.dto;

import java.sql.Date;
import java.util.Objects;

public record UsersDto (
        long id,
        String email,
        String passwordHash,
        String fullName,
        UserRolesDto role,
        boolean isBlocked,
        Date createdAt

) implements IDto {

    @Override
    public String toMenuTableRow() {
        return String.format("%s | %s | %s | %s | %tF %<tR", email, fullName, role.name(), isBlocked ? "Заблокирован" : "Активен", createdAt);
    }

    public static String getMenuTableHeader() {
        return "№ | Email | ФИО | Роль | Статус | Дата регистрации";
    }

    public UsersDto {
        Objects.requireNonNull(email);
        Objects.requireNonNull(passwordHash);
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(role);
        Objects.requireNonNull(createdAt);
    }
}
