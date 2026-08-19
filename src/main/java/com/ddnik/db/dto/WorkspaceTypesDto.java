package com.ddnik.db.dto;

import com.ddnik.db.IDto;

import java.util.Objects;

public record WorkspaceTypesDto (
        Long id,
        String name,
        int minParticipantsCount,
        Integer maxParticipantsCount,
        String nameRus
) implements IDto {

    @Override
    public String toMenuTableRow() {
        return String.format("%s | %d | %d", nameRus, minParticipantsCount, maxParticipantsCount);
    }

    public static String getMenuTableHeader() {
        return "№ | Название | Минимальная вместимость | Максимальная вместимость";
    }

    public WorkspaceTypesDto {
        Objects.requireNonNull(name);
        Objects.requireNonNull(nameRus);
    }

    public WorkspaceTypesDto (String name, Integer maxParticipantsCount, int minParticipantsCount, String nameRus) {
        this(null, name, maxParticipantsCount, minParticipantsCount, nameRus);
    }
}
