package com.ddnik.db.entity;

import java.util.Objects;

public record DayTypes (
        Long id,
        String name
) {

    public DayTypes {
        Objects.requireNonNull(name);
    }

    public DayTypes(String name) {
        this(null, name);
    }
}
