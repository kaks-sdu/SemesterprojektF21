package io.github.arkobat.semesterprojektF21.common;

/**
 * An enum for representing the different color properties
 */
public enum Color {

    /**
     * Orange color
     */
    ORANGE("Orange", 0.74901960784f, 0.43529411764f, 0.29019607843f),
    /**
     * Green color
     */
    GREEN("Green", 0.11764705882f, 0.43529411764f, 0.31372549019f),
    /**
     * Blue color
     */
    BLUE("Blue", 0f, 0.59607843137f, 0.86274509803f),
    /**
     * White Color, A universal color that everything can collide with
     */
    ALL("All", 1f, 1f, 1f);

    private final String name;
    private final float red, green, blue;

    /**
     * A constructor for creating a Color
     * @param name the name of the color
     * @param red the red float value
     * @param green the green float value
     * @param blue the blue float value
     */
    Color(String name, float red, float green, float blue) {
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * A method getting the name in lowercase
     * @return the name in lowercase
     */
    public String lowerCase() {
        return this.name.toLowerCase();
    }

    /**
     * A method for getting the name in uppercase
     * @return the name in uppercase
     */
    public String upperCase() {
        return this.name().toUpperCase();
    }

    /**
     * A method for getting the red float value
     * @return the red float value
     */
    public float getRed() {
        return red;
    }

    /**
     * A method for getting the green float value
     * @return the green float value
     */
    public float getGreen() {
        return green;
    }

    /**
     * A method for getting the blue float value
     * @return the blue float value
     */
    public float getBlue() {
        return blue;
    }
}
