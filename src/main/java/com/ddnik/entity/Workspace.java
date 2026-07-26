package com.ddnik.entity;

public class Workspace {
    private final Long id;
    private final String type;
    private final String name;
    private final int capacity;
    private final float hourly_rate;
    private final boolean is_active;

    public Workspace(Long id, String type, String name, int capacity, float hourly_rate, boolean is_active) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.capacity = capacity;
        this.hourly_rate = hourly_rate;
        this.is_active = is_active;
    }

    public Workspace(boolean is_active, float hourly_rate, int capacity, String name, String type) {
        this.id = null;
        this.is_active = is_active;
        this.hourly_rate = hourly_rate;
        this.capacity = capacity;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getHourly_rate() {
        return hourly_rate;
    }

    public boolean isIs_active() {
        return is_active;
    }
}
