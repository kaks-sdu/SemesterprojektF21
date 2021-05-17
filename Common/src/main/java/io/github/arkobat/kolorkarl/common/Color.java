package io.github.arkobat.kolorkarl.common;


public enum Color {

    ORANGE("Orange", 0.74901960784f, 0.43529411764f, 0.29019607843f),
    GREEN("Green", 0.11764705882f, 0.43529411764f, 0.31372549019f),
    BLUE("Blue", 0f, 0.59607843137f, 0.86274509803f),
    ALL("All", 1f, 1f, 1f);

    private final String name;
    private final float red, green, blue;

    Color(String name, float red, float green, float blue) {
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
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

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }
}
