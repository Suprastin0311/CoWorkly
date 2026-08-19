package com.ddnik.db.dto;

import com.ddnik.db.IDto;

import java.sql.Date;
import java.util.Objects;

public record UsersDto (
        long id,
        String email,
        String passwordHash,
        String fullName,
        String role,
        boolean isBlocked,
        Date createdAt

) implements IDto {

    public UsersDto {
        Objects.requireNonNull(email);
        Objects.requireNonNull(passwordHash);
        Objects.requireNonNull(fullName);
        Objects.requireNonNull(role);
        Objects.requireNonNull(createdAt);
    }

    @Override
    public String toMenuTableRow() {
        return String.format("%s | %s | %s | %s | %tF %<tR", email, fullName, role, isBlocked ? "Заблокирован" : "Активен", createdAt);
    }

    public static String getMenuTableHeader() {
        return " № | Email | ФИО | Роль | Статус | Дата регистрации";
    }
}
