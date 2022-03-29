package com.bootcampjava.starwars.model;

public class Jedi {

    private int id;
    private String name;
    private int strength;
    private int version;

    public Jedi(int id, String name, int strength, int version) {
        this.id = id;
        this.name = name;
        this.strength = strength;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStrength() {
        return strength;
    }

    public int getVersion() {
        return version;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
