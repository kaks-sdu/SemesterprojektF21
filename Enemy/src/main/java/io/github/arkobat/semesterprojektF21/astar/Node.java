package io.github.arkobat.semesterprojektF21.astar;

import io.github.arkobat.semesterprojektF21.common.Location;
import org.jetbrains.annotations.NotNull;

public class Node implements Comparable {

    public Node parent;
    public Location location;
    public int g, h, f;

    public Node(Node parent, Location location){
        this.parent = parent;
        this.location = location;
        g = 0;
        h = 0;
        f = 0;
    }

    // Add own equals method as list.contains(object) uses the equals method to check whether it is inside
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node aStarNode = (Node) o;
        return location.getX() == aStarNode.location.getX() && location.getY() == aStarNode.location.getY();
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Node that = (Node) o;
        return (this.g + this.h) - (that.g + that.h);
    }
}
