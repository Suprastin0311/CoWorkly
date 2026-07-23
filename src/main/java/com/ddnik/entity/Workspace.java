package com.ddnik.entity;

public class Workspace {
    private long id;
    private String type;
    private String name;
    private int capacity;
    private float hourly_rate;
    private boolean is_active;

    public Workspace(long id, String type, String name, int capacity, float hourly_rate, boolean is_active) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.capacity = capacity;
        this.hourly_rate = hourly_rate;
        this.is_active = is_active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getHourly_rate() {
        return hourly_rate;
    }

    public void setHourly_rate(float hourly_rate) {
        this.hourly_rate = hourly_rate;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
