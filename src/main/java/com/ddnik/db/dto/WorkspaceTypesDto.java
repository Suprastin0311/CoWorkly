package com.ddnik.db.dto;

import java.util.Objects;

public record WorkspaceTypesDto (
        long id,
        String name,
        int minParticipantsCount,
        Integer maxParticipantsCount,
        String nameRus
) implements IDto {

    @Override
    public String toMenuTableRow() {
        return String.format("%s | %d | %s", nameRus, minParticipantsCount, maxParticipantsCount == 0 ? "<не указано>" : maxParticipantsCount);
    }

    public static String getMenuTableHeader() {
        return "№ | Название | Минимальная вместимость | Максимальная вместимость";
    }

    public WorkspaceTypesDto {
        Objects.requireNonNull(name);
        Objects.requireNonNull(nameRus);
        maxParticipantsCount = maxParticipantsCount == 0 ? Integer.MAX_VALUE : maxParticipantsCount;
    }
}
