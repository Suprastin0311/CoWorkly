package com.ddnik.db.entity;

import java.util.Objects;

public record BookingStatuses (
        Long id,
        String name
) {

    public BookingStatuses {
        Objects.requireNonNull(name);
    }

    public BookingStatuses(String name) {
        this(null, name);
    }
}
