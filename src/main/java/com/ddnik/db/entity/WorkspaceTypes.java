package com.ddnik.db.entity;

import com.ddnik.db.dto.IDto;

import java.util.Objects;

public record WorkspaceTypes (
        Long id,
        String name,
        Integer maxParticipantsCount,
        int minParticipantsCount,
        String nameRus
) implements IDto
{
    @Override
    public String toMenuRow() {
        StringBuilder row = new StringBuilder();
        row.append(nameRus).append(" | ").append(minParticipantsCount).append(" | ").append(maxParticipantsCount == 0 ? "<не ограничено>" : maxParticipantsCount );
        return row.toString();
    }

    public WorkspaceTypes {
        Objects.requireNonNull(name);
        Objects.requireNonNull(minParticipantsCount);
        Objects.requireNonNull(nameRus);
    }

    public WorkspaceTypes (String name, Integer maxParticipantsCount, int minParticipantsCount, String nameRus) {
        this(null, name, maxParticipantsCount, minParticipantsCount, nameRus);
    }
}

