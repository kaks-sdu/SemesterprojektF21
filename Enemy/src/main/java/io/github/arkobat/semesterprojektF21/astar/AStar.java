package io.github.arkobat.semesterprojektF21.astar;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.commonWorld.WorldTemp;
import io.github.arkobat.semesterprojektF21.enemy.Enemy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    private final int WALL = 100;
    private final double LEAST_DISTANCE = 10; // Least distance before a node has been reached. Higher = less accurate, lower = more accurate. 10 for jumping on platforms
    private final int SECONDS_BEFORE_STOPPING = 5; // If the AI gets stuck, this is the time to wait before cancelling the current pathfind and finding a new one
    private  final int JUMP_COST = 7;

    private Enemy entity;
    private int[][] map;
    private TiledMapTileLayer tiledMapTileLayer;
    private Location startLocation;
    private List<Node> path;
    private boolean isRunning;
    private long startTime;

    // Based on pseudo code: https://www.geeksforgeeks.org/a-search-algorithm/


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
                }else if(containsProperty("spikes", new Location(x, y))){
                    map[x][y] = 200; // TODO: SPIKES
                } else  {
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
                //System.out.println("Enemy reached checkpoint");
            }

            if(currentLocation.getX() < nodeLocation.getX()){
                // move right
                entity.getVelocity().setX(entity.getSpeed());
            }
            if(currentLocation.getX() > nodeLocation.getX()){
                // move left
                entity.getVelocity().setX(-entity.getSpeed());
            }

           /* System.out.println("Is on ground? " + isOnGround());
            System.out.println("Node instruction: " + node.getInstruction());*/
            //System.out.println("Is ground below? " + isGroundBelow(node));


            if(node.getInstruction() == Node.Instruction.JUMP && isOnGround()){
                entity.getVelocity().setY(entity.getJumpHeight());
            }


           /* if(node.getInstruction() == Node.Instruction.JUMP && isOnGround()){
                entity.getVelocity().setY(75f);
            }
*/
            // And is on ground
            /*if(currentLocation.getY() < nodeLocation.getY() && node.getInstruction() == Node.Instruction.JUMP){
                // jump
                if(isOnGround())
                    entity.getVelocity().setY(75f);
            }*/
            /*if(node.getInstruction() == Node.Instruction.JUMP){
                entity.getVelocity().setY(75f);
            }*/

            // No more path to follow, so algorithm does not run anymore
            if(path.isEmpty()){
                //System.out.println("Enemy reached goal!");
                // Stop x movement.
                entity.getVelocity().setX(0);
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

        //System.out.println("Current Location: " + entity.getLocation().getX() + ", " + entity.getLocation().getY());
        //System.out.println("End Location: " + endLocation.getX() + ", " + endLocation.getY());
        /*
        for(Node node : path){
            System.out.println("Node waypoint: " + node.convertToLocation().getX() + ", " + node.convertToLocation().getY());
        }
        System.out.println("End pathway points");*/

        isRunning = true;
    }

    // TODO: Track method to track another entity?

    // Returns null if no path
    private List<Node> findPath(Location endLocation){
        // Check if end location is a valid location i.e. it's not over spikes (as this will make it so the AI cannot ever find the goal)
        if(!isValidNode(endLocation)){
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
        while(!open.isEmpty()){
            // Find the node with the lowest f score in the open list. Priority queue is always sorted, so this will always be the first. Also pop/remove it
            Node currentNode = open.poll();

            // Generate successors i.e. find the neighbours of the current node
            List<Node> children = findNeighborsOptimized(currentNode, false);

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
                    //displayPath(path);
                    //TODO: The display path can be used for the report as it will display the matrix we use for the A* algorithm
                    return path;
                }

                //child.g = currentNode.g + 1; // This is the movement cost from starting point to this node. See neighbours instead
                // Heuristic function
                child.h = Math.abs(child.location.getX() - endLocation.getX()) + Math.abs(child.location.getY() - endLocation.getY()); // Manhattan distance

                // Use the euclidean distance
                //child.h = Math.sqrt(Math.abs((child.location.getX() - endLocation.getX()) * 2 + (child.location.getY() - endLocation.getY()) * 2)); // Euclidean distance
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
                if(open.contains(child)){ //TODO: Read up on https://stackoverflow.com/questions/6997604/a-algorithm-not-working-properly for checking if it contains
                    continue;
                }

                if(closed.contains(child)){
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
        System.out.println("Found no path!");
        return null;
    }

    // If a node with position already exists in the open list, and it has a lower f score, then there is no reason to expand that node
    private boolean skipChild(Node node, PriorityQueue<Node> open){
        //System.out.println("Looping through " + open.size() + " entries...");
        double nodeF = node.f;
        for(Node openNode : open){
            if(node.location.getX() == openNode.location.getX() && node.location.getY() == openNode.location.getY()){
                if(openNode.f < node.f){
                    //System.out.println("Done!");
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
                new Location(-1, 0), // TODO: Read up on https://stackoverflow.com/questions/26615312/how-to-straighten-unneeded-turns-in-a-a-graph-search-result
                new Location(1, 0),
                new Location(3, 0), // Jump +3 tiles to the right
                new Location(-3, 0), // Jump +3 tiles to the left
        };

        for(Location newPosition : adjacentLocations){
            // Get adjacent node position
            Location nodePosition = new Location(node.location.getX() + newPosition.getX(), node.location.getY() + newPosition.getY());

            // Make sure the node is withing the maze boundary
            if(nodePosition.getX() > map.length - 1 || nodePosition.getX() < 0 || nodePosition.getY() > map[0].length - 1 || nodePosition.getY() < 0){
                continue;
            }

            // Make sure it is a valid child i.e going to this node won't make it so it falls down into spikes
            // TODO: This makes the game freeze when having to calculate a position to it in the air it seems.
            /*if(!isValidNode(nodePosition)){
                continue;
            }*/

            // If it is not a wall
            if (map[(int) nodePosition.getX()][ (int) nodePosition.getY()] != WALL) {


                Node child = new Node(node, nodePosition);

                // If there is not ground below this node, then it's not a valid location
                if(!isGroundBelow(child)) continue;

                // Distance from original position to new position. Euclidean
                int distanceToLocation = (int) Math.sqrt( (node.location.getX() - newPosition.getX()) * 2 + (node.location.getY() - newPosition.getY()) * 2);

                // If node is a jump or a drop
                /*if(newPosition.getY() > 0 ||  newPosition.getY() < 0){
                    // +movement cost for jumping and dropping
                    child.g = node.g + JUMP_COST;
                }else{
                    child.g = node.g + 1;
                }*/

                // Set nodes instruction (either jump or walk)
                if(newPosition.getX() > 1 || newPosition.getX() == -3 || newPosition.getY() >= 1){ // jump 2+ x tiles or 1+ y tile
                    child.setInstruction(Node.Instruction.JUMP);
                    child.g = node.g + JUMP_COST;
                }else{

                    if(newPosition.getX() < 0){
                        child.setInstruction(Node.Instruction.WALK_LEFT);
                    }else{
                        child.setInstruction(Node.Instruction.WALK_RIGHT);
                    }
                    //child.setInstruction(Node.Instruction.WALK);
                    child.g = node.g + 1; // Walk cost
                }

                neighbours.add(child);
            }
        }

        // Shuffling the neighbors makes the AStar algorithm take a more 'random' path
        if(shuffle)
            Collections.shuffle(neighbours);

        return neighbours;
    }

    // Check whether the node will make it so the player ends up falling into a spike
    // TODO: Can't do this, as it will make it so the ai can't go down and strafe to a new spot, I'm pretty sure
    // TODO: Save in memory on map start instead of checking everytime, as some maps can be very big. Instead just check rows and dont path find to rows that are bad
    private boolean isValidNode(Location nodeLocation){
        for(int y = (int) nodeLocation.getY(); y >= 0; y--){
            if(map[ (int) nodeLocation.getX()][y] == 200){ // TODO: Or if it is down in the void. 200 = spikes
                return false;
            }else if(map[ (int) nodeLocation.getX()][y] == WALL){ // If it finds the ground before it finds spikes, then it is a valid position
                return true;
            }
        }
        return true;
    }

    // The Manhattan distance from the node to the enemy. This is used to not add nodes that are too far away from the enemy (i.e. jump height)
    /*private boolean nodeDistanceFrom(Enemy entity){

    }
*/
    private boolean isGroundBelow(Node node){
        // If outside map boundaries
        if(node.location.getX() > map.length - 1 || node.location.getX() < 0 || node.location.getY() > map[0].length - 1 || node.location.getY() < 0) return false;

        int index = Math.round(node.location.getY()-1);
        if(index == -1) return false;

        return map[Math.round(node.location.getX())][index] == WALL;
    }

    private void displayPath(List<Node> path){

        for(Node node : path){
            System.out.println("Node instruction: " + node.getInstruction());

            System.out.println("Nodes f: " + node.f + "  | g: " + node.g + " | h " + node.h);
            System.out.println("Node location: " + node.location.getX() + ", " + node.location.getY());

            System.out.println("(" + node.location.getX() + ", " + node.location.getY() + ")");
            map[(int) node.location.getX()][(int) node.location.getY()] = 99;
        }

       /* for(int i = 1; i < path.size(); i++){
            Node node = path.get(i);
            Node previousNode = path.get(i-1);

            *//*if(node.location.getX() > previousNode.location.getX()){
                // move right
                System.out.println("Move right");
            }else if(node.location.getX() < previousNode.location.getX()){
                // move left
                System.out.println("Move left");
            }else if(node.location.getY() > previousNode.location.getY()){
                // jump
                System.out.println("Jump");
            }*//*

            System.out.println("Node instruction: " + node.getInstruction());

            System.out.println("(" + previousNode.location.getX() + ", " + previousNode.location.getY() + ")");
            map[(int) node.location.getX()][(int) node.location.getY()] = 99;
        }*/

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

    @Deprecated
    private boolean isOnGround(){
        return containsProperty("collision", new Location(entity.getLocation().getX()/8, entity.getLocation().getY()/8-1));
    }

    private boolean containsProperty(String property, Location location){
        TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell( (int) location.getX(), (int) location.getY()); //might need better conversion in future
        if (cell == null) return false;
        TiledMapTile tile = cell.getTile();
        MapProperties properties = tile.getProperties();
        return properties.containsKey(property);
    }
}
