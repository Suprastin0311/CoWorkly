package com.ddnik.entity;

public class DayTypes {
    private final Long id;
    private final String name;

    public DayTypes(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
