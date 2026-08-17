package com.ddnik.db.entity;

import java.util.Objects;

public record WorkspaceTypes(
        Long id,
        String name,
        Integer maxParticipantsCount,
        int minParticipantsCount,
        String nameRus
)
{
    public WorkspaceTypes {
        Objects.requireNonNull(name);
        Objects.requireNonNull(nameRus);
    }

    public WorkspaceTypes(String name, Integer maxParticipantsCount, int minParticipantsCount, String nameRus) {
        this(null, name, maxParticipantsCount, minParticipantsCount, nameRus);
    }
}

