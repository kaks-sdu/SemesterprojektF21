package io.github.arkobat.semesterprojektF21.common;

public enum Color {

    ORANGE("Orange"),
    GREEN("Green"),
    BLUE("Blue"),
    ALL("All");

    private final String name;

    Color(String name) {
        this.name = name;
    }

    public String lowerCase() {
        return this.name.toLowerCase();
    }

    public String camelCase() {
        return this.name;
    }

    public String upperCase() {
        return this.name();
    }

}
