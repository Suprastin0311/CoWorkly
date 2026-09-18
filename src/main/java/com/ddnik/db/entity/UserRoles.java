package com.ddnik.db.entity;

import java.util.Objects;

public record UserRoles (
        Long id,
        String name
) {

    public UserRoles {
        Objects.requireNonNull(name);
    }

    public UserRoles(String name) {
        this(null, name);
    }
}
