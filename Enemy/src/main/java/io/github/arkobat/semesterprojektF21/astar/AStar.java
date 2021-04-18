package io.github.arkobat.semesterprojektF21.astar;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;

import java.util.*;

public class AStar {

    private int[][] map;
    private Location startLocation, endLocation;
    private TiledMapTileLayer tiledMapTileLayer;
    private final int WALL = 100;

    public AStar(Entity entity, Location endLocation){
        tiledMapTileLayer = ((WorldTemp) entity.getWorld()).getCollisionLayer();
        this.map = new int[tiledMapTileLayer.getWidth()][tiledMapTileLayer.getHeight()];

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
        startLocation = new Location(4, 11);
        endLocation = new Location(16, 11);

        //map[(int) startLocation.getX()][(int) startLocation.getY()] = 22;
        //map[(int) endLocation.getX()][(int) endLocation.getY()] = 33;

        // Dunno why, but the map is always sideways
        displayMap();

        findPath(startLocation, endLocation);
    }

    // Returns null if no path
    public List<Node> findPath(Location startLocation, Location endLocation){
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
                    System.out.println("Found path: ");
                    List<Node> path = new ArrayList<>();
                    path.add(new Node(null, endLocation)); // Add end location too. Can be ommitted
                    Node current = currentNode;
                    while(current != null){
                        path.add(current);
                        current = current.parent;
                    }
                    displayPath(path);
                    //TODO: Don't display path
                    return path;
                }

                // Create the f, g, and h values
                child.g = currentNode.g + 1; // This is the movement cost from starting point to this node
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
        System.out.println("No path found");
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

    private List<Node> findNeighborsOptimized(Node node, boolean shuffle){
        List<Node> neighbours = new ArrayList<>(8); // Reserve only 8 nodes
        Location[] adjacentLocations = {
                new Location(0, -1),
                new Location(0, 1),
                new Location(-1, 0),
                new Location(1, 0),
                new Location(-1, -1),
                new Location(-1, 1),
                new Location(1, -1),
                new Location(1, 1),
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
                neighbours.add(new Node(node, nodePosition));
            }
        }

        // Shuffling the neighbors makes the AStar algorithm take a more 'random' path
        if(shuffle)
            Collections.shuffle(neighbours);

        return neighbours;
    }

    private void displayPath(List<Node> path){
        Collections.reverse(path);
        for(Node node : path){
            System.out.println("(" + node.location.getX() + ", " + node.location.getY() + ")");
            map[(int) node.location.getX()][(int) node.location.getY()] = 99;
        }
        displayMap();
    }

    public void displayMap(){
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
