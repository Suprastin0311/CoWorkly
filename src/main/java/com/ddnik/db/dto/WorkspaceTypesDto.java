package com.ddnik.db.dto;

import com.ddnik.db.IDto;

import java.util.Objects;

public record WorkspaceTypesDto (
        Long id,
        String name,
        Integer maxParticipantsCount,
        int minParticipantsCount,
        String nameRus
) implements IDto {

    @Override
    public String toMenuRow() {
        StringBuilder row = new StringBuilder();
        row.append(nameRus).append(" | ").append(minParticipantsCount).append(" | ").append(maxParticipantsCount == 0 ? "<не ограничено>" : maxParticipantsCount );
        return row.toString();
    }

    public WorkspaceTypesDto {
        Objects.requireNonNull(name);
        Objects.requireNonNull(nameRus);
    }

    public WorkspaceTypesDto (String name, Integer maxParticipantsCount, int minParticipantsCount, String nameRus) {
        this(null, name, maxParticipantsCount, minParticipantsCount, nameRus);
    }
}
