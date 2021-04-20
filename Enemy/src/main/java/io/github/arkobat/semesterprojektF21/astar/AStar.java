package io.github.arkobat.semesterprojektF21.astar;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;
import io.github.arkobat.semesterprojektF21.enemy.Enemy;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    private final int WALL = 100;
    private final double LEAST_DISTANCE = 1.2; // Least distance before a node has been reached. Higher = less accurate, lower = more accurate
    private final int SECONDS_BEFORE_STOPPING = 5; // If the AI gets stuck, this is the time to wait before cancelling the current pathfind and finding a new one

    private Enemy entity;
    private int[][] map;
    private TiledMapTileLayer tiledMapTileLayer;
    private int jumpCost = 2;
    private Location startLocation;
    private List<Node> path;
    private boolean isRunning;
    private long startTime;


    public AStar(Enemy entity){
        this.entity = entity;
        this.path = new ArrayList<>();
        tiledMapTileLayer = ((WorldTemp) entity.getWorld()).getCollisionLayer();
        this.map = new int[tiledMapTileLayer.getWidth()][tiledMapTileLayer.getHeight()];
        isRunning = false;

        // Fill map
        for(int x = 0; x < tiledMapTileLayer.getWidth(); x++){
            for(int y = 0; y < tiledMapTileLayer.getHeight(); y++){

                // If there is a wall at the current location
                if(containsProperty("collision", new Location(x, y))){
                    map[x][y] = WALL;
                }else {
                    map[x][y] = 0;
                }
            }
        }

        //TODO: Change to entity's location
        // map is sideways, so what you think is x is actually y. tilt ur head
        // Right now it only completes a maze, and thus not take into account having to jump and where it will land.
        // Could be fixable by telling it to jump if there is a gap infront of it, or if the path goes upwards.
        int tilesPerPixel = 8;

        int entityX = (int) entity.getLocation().getX() / tilesPerPixel;
        int entityY = (int) entity.getLocation().getY() / tilesPerPixel - 1; // -1 since it goes from the head

        System.out.println("x: " + entityX + ", y: " + entityY);

        startLocation = new Location(entityX, entityY);

        // Dunno why, but the map is always sideways
        displayMap();
    }

    /**
     * Update the algorithm
     */
    public void process(){
        // If the ai is running, then find a path
        if(isRunning){
            // Get initial node
            Node node = path.get(0);

            Location nodeLocation = node.convertToLocation(); // target location
            Location currentLocation = entity.getLocation();

            // Calculate distance from current location to target location
            double distance = Math.sqrt( Math.pow(currentLocation.getY() - nodeLocation.getY(), 2) + Math.pow(currentLocation.getX() - nodeLocation.getX(), 2) );

            // If the distance is close enough
            if(distance < LEAST_DISTANCE){
                // Done following to this node, so remove it
                path.remove(0);
            }

            if(currentLocation.getX() < nodeLocation.getX()){
                // move right
                entity.getVelocity().setX(entity.getSpeed());
            }else {
                // move left
                entity.getVelocity().setX(-entity.getSpeed());
            }

            if(currentLocation.getY() < nodeLocation.getY()){
                // jump
                entity.getVelocity().setY(75f);
            }

            // No more path to follow, so algorithm does not run anymore
            if(path.isEmpty()){
                // Stop x movement.
                entity.getVelocity().setX(0);
                entity.getVelocity().setY(0);
                isRunning = false;
            }
        }
    }

    // Goto (location)
    public void gotoLocation(Location endLocation){
        // If it's been long enough without finding the path, then cancel
        if(((System.currentTimeMillis() - startTime) / 1000) > SECONDS_BEFORE_STOPPING){
            System.out.println("Cancelled pathfinding due to time constraints");
            isRunning = false;
            path = null;
        }

        // If the ai is already going to a location, then wait for it to finish
        if(isRunning){
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

        if(path == null || path.isEmpty()){
            // Found no path
            System.out.println("Found no path to specified location");
            return;
        }

        /*System.out.println("Current Location: " + entity.getLocation().getX() + ", " + entity.getLocation().getY());
        System.out.println("End Location: " + endLocation.getX() + ", " + endLocation.getY());

        for(Node node : path){
            System.out.println("Node waypoint: " + node.convertToLocation().getX() + ", " + node.convertToLocation().getY());
        }
        System.out.println("End pathway points");*/

        isRunning = true;
    }

    // TODO: Track method to track another entity?

    // Returns null if no path
    private List<Node> findPath(Location endLocation){
        // Initialize open and closed lists
        PriorityQueue<Node> open = new PriorityQueue<>(); // Priority queue is like a heap q. Using normal ArrayList will mean that every pop, we have to scan the WHOLE list. See https://stackoverflow.com/questions/60853524/astar-pathfinding-visualization-is-incredibly-slow-python
        PriorityQueue<Node> closed = new PriorityQueue<>();

        Node startNode = new Node(null, startLocation);

        // Add start node to open list
        open.offer(startNode);

        // While the open list is not empty
        while(!open.isEmpty()){
            // Find the node with the lowest f score in the open list. Priority queue is always sorted, so this will always be the first. Also pop/remove it
            Node currentNode = open.poll();

            // Generate successors i.e. find the neighbours of the current node
            List<Node> children = findNeighborsOptimized(currentNode, true);

            // For each child
            for(Node child : children){

                // If the child is the goal then stop the search
                if(child.location.getX() == endLocation.getX() && child.location.getY() == endLocation.getY()){
                    List<Node> path = new ArrayList<>();
                    path.add(new Node(null, endLocation)); // Add end location too. Can be ommitted
                    Node current = currentNode;
                    while(current != null){
                        path.add(current);
                        current = current.parent;
                    }
                    path.remove(0); // Remove initial node, since it is the starting point, and we don't want that to be in the path too
                    Collections.reverse(path); // We have to reverse the path first

                    //TODO: Go to path
                    return path;
                }

                // Create the f, g, and h values
                //child.g = currentNode.g + 1; // This is the movement cost from starting point to this node. See neighbours instead
                // Heuristic function
                //child.h = Math.abs(child.location.getX() - endLocation.getX()) + Math.abs(child.location.getY() - endLocation.getY()); // Manhattan distance

                // Use the euclidean distance
                child.h = (int) Math.sqrt( (child.location.getX() - endLocation.getX()) * 2 + (child.location.getY() - endLocation.getY()) * 2); // Euclidean distance

                // 0 for Dijkstra's Algorithm
                //child.h = 0;
                child.f = child.g + child.h;

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
                if(open.contains(child)){
                    continue;
                }

                if(skipChild(child, closed)){
                    continue; // Skip this child
                }else {
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

    // If a node with position already exists in the closed list, and it has a lower f score, then there is no reason to expand that node
    private boolean skipChild(Node node, PriorityQueue<Node> closed){
        for(Node closedNode : closed){
            if(node.location.getX() == closedNode.location.getX() && node.location.getY() == closedNode.location.getY()){
                if(closedNode.f < node.f){
                    return true;
                }
            }
        }
        return false;
    }

    // TODO: A neighbour can also be one where you jump to reach it
    private List<Node> findNeighborsOptimized(Node node, boolean shuffle){
        List<Node> neighbours = new ArrayList<>(8); // Reserve only 8 nodes
        Location[] adjacentLocations = {
                new Location(0, -1),
                new Location(0, 1),
                new Location(-1, 0),
                new Location(1, 0),
                new Location(2, 3), // Jump + 2 tiles forward and +3 tiles upwards
               /* new Location(-1, -1), // Diagonals. Skip for now, but can be used to implement jumping. Just insert it as a valid neighbor, and calculate it's
                new Location(-1, 1),    // Cost in child.g + distance
                new Location(1, -1),
                new Location(1, 1),*/
        };

        for(Location newPosition : adjacentLocations){
            // Get adjacent node position
            Location nodePosition = new Location(node.location.getX() + newPosition.getX(), node.location.getY() + newPosition.getY());

            // Make sure the node is withing the maze boundary
            if(nodePosition.getX() > map.length - 1 || nodePosition.getX() < 0 || nodePosition.getY() > map[0].length - 1 || nodePosition.getY() < 0){
                continue;
            }

            // If it is not a wall
            if (map[(int) nodePosition.getX()][ (int) nodePosition.getY()] != WALL) {


                Node child = new Node(node, nodePosition);
                // Distance from original position to new position. Euclidean
                int distanceToLocation = (int) Math.sqrt( (node.location.getX() - newPosition.getX()) * 2 + (node.location.getY() - newPosition.getY()) * 2);

                // If node is a jump
                if(newPosition.getY() > 0){
                    // +2 movement cost for jumping
                    child.g = node.g + distanceToLocation + jumpCost;
                }else{
                    child.g = node.g + distanceToLocation;
                }

                neighbours.add(child);
            }
        }

        // Shuffling the neighbors makes the AStar algorithm take a more 'random' path
        if(shuffle)
            Collections.shuffle(neighbours);

        return neighbours;
    }

    private void displayPath(List<Node> path){
        Collections.reverse(path);

        for(int i = 1; i < path.size(); i++){
            Node node = path.get(i);
            Node previousNode = path.get(i-1);

            if(node.location.getX() > previousNode.location.getX()){
                // move right
                System.out.println("Move right");
            }else if(node.location.getX() < previousNode.location.getX()){
                // move left
                System.out.println("Move left");
            }else if(node.location.getY() > previousNode.location.getY()){
                // jump
                System.out.println("Jump");
            }

            System.out.println("(" + previousNode.location.getX() + ", " + previousNode.location.getY() + ")");
            map[(int) node.location.getX()][(int) node.location.getY()] = 99;
        }

        /*for(Node node : path){
            System.out.println("(" + node.location.getX() + ", " + node.location.getY() + ")");
            map[(int) node.location.getX()][(int) node.location.getY()] = 99;
        }*/
        displayMap();
    }

    private void displayMap(){
        for(int x = 0; x < map.length; x++){
            for(int y = 0; y < map[0].length; y++) {
                System.out.printf("%5d", map[x][y]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean containsProperty(String property, Location location){
        TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell( (int) location.getX(), (int) location.getY()); //might need better conversion in future
        if (cell == null) return false;
        TiledMapTile tile = cell.getTile();
        MapProperties properties = tile.getProperties();
        return properties.containsKey(property);
    }
}
