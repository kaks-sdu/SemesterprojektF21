package io.github.arkobat.semesterprojektF21.enemy.astar;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.common.Color;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;
import io.github.arkobat.semesterprojektF21.enemy.Enemy;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    private final static int WALL = 100;
    private final static int SPIKE = 200;
    private final static double LEAST_DISTANCE = 6; // Least distance before a node has been reached. Higher = less accurate, lower = more accurate. 10 for jumping on platforms
    private final static int SECONDS_BEFORE_STOPPING = 5; // If the AI gets stuck, this is the time to wait before cancelling the current pathfind and finding a new one
    private final static int JUMP_COST = 7;

    private final Enemy entity;
    private final int[][] map;
    private final TiledMapTileLayer tiledMapTileLayer;

    private Location startLocation;
    private List<Node> path;
    private boolean isRunning;
    private long startTime;

    // Based on pseudo code: https://www.geeksforgeeks.org/a-search-algorithm/


    public AStar(Enemy entity) {
        this.entity = entity;
        this.path = new ArrayList<>();
        this.tiledMapTileLayer = ((WorldTemp) entity.getWorld()).getCollisionLayer();
        this.map = new int[tiledMapTileLayer.getWidth()][tiledMapTileLayer.getHeight()];
        this.isRunning = false;

        // Fill map
        for (int x = 0; x < tiledMapTileLayer.getWidth(); x++) {
            for (int y = 0; y < tiledMapTileLayer.getHeight(); y++) {
                MapProperties properties = getProperties(new Location(x, y));
                // If there is a wall at the current location
                if (properties.containsKey("collision")) {
                    if (!matchesColor(properties)) continue;
                    map[x][y] = WALL;
                } else if (properties.containsKey("spikes")) {
                    if (!matchesColor(properties)) continue;
                    map[x][y] = SPIKE;
                }
            }
        }

        int tilesPerPixel = 8;

        int entityX = (int) entity.getLocation().getX() / tilesPerPixel;
        int entityY = (int) entity.getLocation().getY() / tilesPerPixel - 1; // -1 since it goes from the head

        //System.out.println("x: " + entityX + ", y: " + entityY);

        startLocation = new Location(entityX, entityY);

        // Dunno why, but the map is always sideways
        // displayMap();
    }

    /**
     * Update the algorithm
     */
    public void process() {
        // If the ai is running, then find a path
        if (isRunning) {
            // Get initial node
            Node node = path.get(0);

            Location nodeLocation = node.convertToLocation(); // target location
            Location currentLocation = entity.getLocation();

            // Calculate distance from current location to target location
            double distance = Math.sqrt(Math.pow(currentLocation.getY() - nodeLocation.getY(), 2) + Math.pow(currentLocation.getX() - nodeLocation.getX(), 2));

            // If the distance is close enough
            if (distance < LEAST_DISTANCE) {
                // Done following to this node, so remove it
                path.remove(0);
                //Enemy reached checkpoint
            }

            if (currentLocation.getX() < nodeLocation.getX()) {
                // move right
                entity.getVelocity().setX(entity.getSpeed());
            }
            if (currentLocation.getX() > nodeLocation.getX()) {
                // move left
                entity.getVelocity().setX(-entity.getSpeed());
            }

            if (node.getInstruction() == Node.Instruction.JUMP && isOnGround()) {
                entity.getVelocity().setY(entity.getJumpHeight());
            }

            // No more path to follow, so algorithm does not run anymore
            if (path.isEmpty()) {
                //System.out.println("Enemy reached goal!");
                // Stop x movement.
                entity.getVelocity().setX(0);
                isRunning = false;
            }
        }
    }

    // Goto (location)
    public void gotoLocation(Location endLocation) {
        // If it's been long enough without finding the path, then cancel
        if (((System.currentTimeMillis() - startTime) / 1000) > SECONDS_BEFORE_STOPPING) {
            // Cancelled pathfinding due to time constraints
            isRunning = false;
            path = null;
        }

        // If the ai is already going to a location, then wait for it to finish
        if (isRunning) {
            return;
        }

        // Set start time for later use if the ai gets stuck
        startTime = System.currentTimeMillis();

        // Convert location to real world coordinates instead of grid based
        int tilesPerPixel = 8;
        int entityX = Math.round(entity.getLocation().getX() / tilesPerPixel);
        int entityY = Math.round(entity.getLocation().getY() / tilesPerPixel);
        startLocation = new Location(entityX, entityY);

        int endX = Math.round(endLocation.getX() / tilesPerPixel);
        int endY = Math.round(endLocation.getY() / tilesPerPixel);
        Location _endLocation = new Location(endX, endY);


        path = findPath(_endLocation);

        if (path == null || path.isEmpty()) {
            // Found no path
            return;
        }

        isRunning = true;
    }

    // TODO: Track method to track another entity?

    // Returns null if no path
    private List<Node> findPath(Location endLocation) {
        // Check if end location is a valid location i.e. it's not over spikes (as this will make it so the AI cannot ever find the goal)
        if (!isValidNode(endLocation)) {
            //End location will never return a path!
            return null;
        }

        // Initialize open and closed lists
        PriorityQueue<Node> open = new PriorityQueue<>(); // Priority queue is like a heap q. Using normal ArrayList will mean that every pop, we have to scan the WHOLE list. See https://stackoverflow.com/questions/60853524/astar-pathfinding-visualization-is-incredibly-slow-python
        PriorityQueue<Node> closed = new PriorityQueue<>();

        Node startNode = new Node(null, startLocation);

        // Add start node to open list
        open.offer(startNode);

        // While the open list is not empty
        while (!open.isEmpty()) {
            // Find the node with the lowest f score in the open list. Priority queue is always sorted, so this will always be the first. Also pop/remove it
            Node currentNode = open.poll();

            // Generate successors i.e. find the neighbours of the current node
            List<Node> children = findNeighborsOptimized(currentNode, false);

            // For each child
            for (Node child : children) {

                // If the child is the goal then stop the search
                if (child.getLocation().getX() == endLocation.getX() && child.getLocation().getY() == endLocation.getY()) {
                    List<Node> path = new ArrayList<>();
                    path.add(new Node(null, endLocation)); // Add end location too. Can be ommitted
                    Node current = currentNode;
                    while (current != null) {
                        path.add(current);
                        current = current.getParent();
                    }
                    Collections.reverse(path); // We have to reverse the path first
                    //displayPath(path);
                    //TODO: The display path can be used for the report as it will display the matrix we use for the A* algorithm
                    return path;
                }

                //child.g = currentNode.g + 1; // This is the movement cost from starting point to this node. See neighbours instead
                // Heuristic function
                child.setH(Math.abs(child.getLocation().getX() - endLocation.getX()) + Math.abs(child.getLocation().getY() - endLocation.getY())); // Manhattan distance

                // Use the euclidean distance
                //child.h = Math.sqrt(Math.abs((child.location.getX() - endLocation.getX()) * 2 + (child.location.getY() - endLocation.getY()) * 2)); // Euclidean distance
                // 0 for Dijkstra's Algorithm
                //child.h = 0;
                child.setF(child.getG() + child.getH());

                /**
                 * Looping through the list takes O(n)
                 */
                /* if(skipChild(child, open)){
                    //System.out.println("Open pq already contains child");
                    continue; // Skip this child
                }*/

                // If a child with same position already exists in the open list and it has a lower f score, then skip this child
                /**
                 * FASTEST. Seems like Java contains method does not have O(n) complexity
                 */
                // We add our own equals method to our node class, so that we don't have to o(n)
                if (open.contains(child)) { //TODO: Read up on https://stackoverflow.com/questions/6997604/a-algorithm-not-working-properly for checking if it contains
                    continue;
                }

                if (closed.contains(child)) {
                    continue; // Skip this child
                } else {
                    // If there is no node in the closed list with same position or lower f score, then add that as a new search area i.e. add it to the open list
                    open.offer(child);
                }
            }

            // Add currentNode to closed list
            closed.offer(currentNode);
        }
        // No path found
        return null;
    }

    // If a node with position already exists in the open list, and it has a lower f score, then there is no reason to expand that node
    private boolean skipChild(Node node, PriorityQueue<Node> open) {
        double nodeF = node.getF();
        for (Node openNode : open) {
            if (node.getLocation().getX() == openNode.getLocation().getX() && node.getLocation().getY() == openNode.getLocation().getY()) {
                if (openNode.getF() < node.getF()) {
                    return true;
                }
            }
        }
        return false;
    }

    // TODO: A neighbour can also be one where you jump to reach it
    private List<Node> findNeighborsOptimized(Node node, boolean shuffle) {
        List<Node> neighbours = new ArrayList<>(8); // Reserve only 8 nodes
        Location[] adjacentLocations = {
                new Location(0, -1),
                new Location(0, 1),
                new Location(-1, 0), // TODO: Read up on https://stackoverflow.com/questions/26615312/how-to-straighten-unneeded-turns-in-a-a-graph-search-result
                new Location(1, 0),
                new Location(3, 0), // Jump +3 tiles to the right
                new Location(-3, 0), // Jump +3 tiles to the left
                new Location(3, 1), // Jump +3 tiles to the right + 1 up
                new Location(3, -1),
                new Location(-3, 1),
                new Location(-3, -1),
                new Location(3, 2), // Jump 3 tiles forward + 2 up
                new Location(-3, 2),
                new Location(3, -2), // Jump 3 tiles forward + 2 tiles down
                new Location(-3, -2),
        };

        for (Location newPosition : adjacentLocations) {
            // Get adjacent node position
            Location nodePosition = new Location(node.getLocation().getX() + newPosition.getX(), node.getLocation().getY() + newPosition.getY());

            // Make sure the node is withing the maze boundary
            if (nodePosition.getX() > map.length - 1 || nodePosition.getX() < 0 || nodePosition.getY() > map[0].length - 1 || nodePosition.getY() < 0) {
                continue;
            }

            // If it is not a wall
            if (map[(int) nodePosition.getX()][(int) nodePosition.getY()] != WALL) {


                Node child = new Node(node, nodePosition);

                // If there is not ground below this node, then it's not a valid location
                if (!isGroundBelow(child)) continue;

                // Set nodes instruction (either jump or walk)
                if (newPosition.getX() > 1 || newPosition.getX() == -3 || newPosition.getY() >= 1) { // jump 2+ x tiles or 1+ y tile
                    child.setInstruction(Node.Instruction.JUMP);
                    child.setG(node.getG() + JUMP_COST);
                } else {
                    if (newPosition.getX() < 0) {
                        child.setInstruction(Node.Instruction.WALK_LEFT);
                    } else {
                        child.setInstruction(Node.Instruction.WALK_RIGHT);
                    }
                    child.setG(node.getG() + 1); // Walk cost
                }

                neighbours.add(child);
            }
        }

        // Shuffling the neighbors makes the AStar algorithm take a more 'random' path
        if (shuffle)
            Collections.shuffle(neighbours);

        return neighbours;
    }

    // Check whether the node will make it so the player ends up falling into a spike
    // TODO: Can't do this, as it will make it so the ai can't go down and strafe to a new spot, I'm pretty sure
    // TODO: Save in memory on map start instead of checking everytime, as some maps can be very big. Instead just check rows and dont path find to rows that are bad
    private boolean isValidNode(Location nodeLocation) {
        for (int y = (int) nodeLocation.getY(); y >= 0; y--) {
            if (map[(int) nodeLocation.getX()][y] == SPIKE) {
                return false;
            } else if (map[(int) nodeLocation.getX()][y] == WALL) { // If it finds the ground before it finds spikes, then it is a valid position
                return true;
            }
        }
        return true;
    }

    private boolean isGroundBelow(Node node) {
        // If outside map boundaries
        if (node.getLocation().getX() > map.length - 1 || node.getLocation().getX() < 0 || node.getLocation().getY() > map[0].length - 1 || node.getLocation().getY() < 0)
            return false;

        int index = Math.round(node.getLocation().getY() - 1);
        if (index == -1) return false;

        return map[Math.round(node.getLocation().getX())][index] == WALL;
    }

    private void displayPath(List<Node> path) {

        for (Node node : path) {
            System.out.println("Node instruction: " + node.getInstruction());

            System.out.println("Nodes f: " + node.getF() + "  | g: " + node.getG() + " | h " + node.getH());
            System.out.println("Node location: " + node.getLocation().getX() + ", " + node.getLocation().getY());

            System.out.println("(" + node.getLocation().getX() + ", " + node.getLocation().getY() + ")");
            map[(int) node.getLocation().getX()][(int) node.getLocation().getY()] = 99;
        }

        // displayMap();
    }

    private void displayMap() {
        if (map.length == 0) return;
        for (int y = map[0].length - 1; y >= 0; y--) {
            for (int x = 0; x < map.length; x++) {
                System.out.printf("%5d", map[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Deprecated
    private boolean isOnGround() {
        return containsProperty("collision", new Location(entity.getLocation().getX() / 8, entity.getLocation().getY() / 8 - 1));
    }

    private boolean containsProperty(String property, Location location) {
        MapProperties properties = getProperties(location);
        return properties.containsKey(property);
    }

    private @NotNull MapProperties getProperties(Location location) {
        TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell((int) location.getX(), (int) location.getY()); //might need better conversion in future
        if (cell == null) return new MapProperties();
        TiledMapTile tile = cell.getTile();
        return tile.getProperties();
    }

    private boolean matchesColor(MapProperties mapProperties) {
        if (!mapProperties.containsKey("color")) return true;
        Color color = Color.valueOf(mapProperties.get("color", String.class));
        if (color == Color.ALL) return true;
        return color == this.entity.getColor();
    }
}
