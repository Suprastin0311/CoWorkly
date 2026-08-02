package com.ddnik.db.entity;

public class UserRoles {
    private final Long id;
    private final String name;

    public UserRoles(Long id, String name) {
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
