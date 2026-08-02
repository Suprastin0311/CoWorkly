package com.ddnik.db.entity;

public class WorkspaceTypes {
    private final Long id;
    private final String name;

    public WorkspaceTypes(Long id, String name) {
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
