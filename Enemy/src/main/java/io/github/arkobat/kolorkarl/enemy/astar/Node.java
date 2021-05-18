package io.github.arkobat.kolorkarl.enemy.astar;

import io.github.arkobat.kolorkarl.common.Location;
import org.jetbrains.annotations.NotNull;

public class Node implements Comparable<Node> {

    private final Node parent;
    private final Location location;
    private Instruction instruction;
    private double g, h, f;

    public Node(Node parent, Location location) {
        this.parent = parent;
        this.location = location;
        this.g = 0;
        this.h = 0;
        this.f = 0;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public Location convertToLocation() {
        return new Location(location.getX() * 8, location.getY() * 8); // 8 pixels per square
    }

    // Add own equals method as list.contains(object) uses the equals method to check whether it is inside
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return this.location.getX() == node.location.getX()
                && this.location.getY() == node.location.getY()
                && node.f < this.f;
    }

    @Override
    public int compareTo(@NotNull Node node) {
        return (int) Math.round((this.g + this.h) - (node.g + node.h));
    }

    public Node getParent() {
        return parent;
    }

    public Location getLocation() {
        return location;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    enum Instruction {
        JUMP, // TODO: Can have small jump and big jump
        WALK_RIGHT,
        WALK_LEFT,
    }

}
