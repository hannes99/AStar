package io.github.hannes99.world;

import io.github.hannes99.a_star.AStar;
import io.github.hannes99.a_star.Node;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import java.util.ArrayList;

/**
 * Manages all the nodes in a world.
 */
public class AStarWorld {
    private final ArrayList<Node3d> allNodes = new ArrayList<>();
    private final ArrayList<Node> lastPath = new ArrayList<>();
    private Node3d start, target;
    private boolean autoConnectToAll;

    /**
     * Creates a new empty world
     */
    public AStarWorld() {
    }

    /**
     * Removes a node and all connections to it
     *
     * @param node The node to remove
     */
    public void destroyNode(Node3d node) {
        allNodes.remove(node); // Remove the node
        // Remove all connections to this node
        for (Node n : node.getConnectionsFrom())
            n.removeConnectionTo(node);
    }

    /**
     * Creates a new node. If autoConnectToAll is set the node will be connected to all the other nodes in this
     * world. The h value is calculated if the target is set.
     *
     * @param x X Position
     * @param y Y Position
     * @param z Z Position
     * @return The created node
     */
    public Node3d createNode(double x, double y, double z) {
        Node3d node = new Node3d(x, y, z);
        allNodes.add(node);
        if (autoConnectToAll) // TODO autoConnect distance in world
            connectToAll(node);
        if (target != null)
            node.setH(target);
        return node;
    }

    /**
     * Connects a node to every other node.
     *
     * @param node The node
     */
    public void connectToAll(Node3d node) {
        allNodes.forEach(n -> {
            double distance = node.getPosition().distance(n.getPosition());
            node.connectTo(n, distance);
            n.connectTo(node, distance);
        });
    }

    /**
     * Returns a list with the last path from end to start. If no path was found the list is empty.
     *
     * @return The list
     */
    public ArrayList<Node> getLastPath() {
        return lastPath;
    }

    /**
     * Returns the closest node to a postion.
     * TODO method for a list sorted by distance?
     *
     * @param p The position
     * @return The closest node to p
     */
    public Node3d getNearestNode(Point3d p) {
        Node3d nearest = null;
        if (allNodes.size() > 0) {
            nearest = allNodes.get(0);
            double dist2 = nearest.getPosition().distanceSquared(p);
            for (Node3d node : allNodes) {
                double d = node.getPosition().distanceSquared(p);
                if (d < dist2) {
                    dist2 = d;
                    nearest = node;
                }
            }
        }
        return nearest;
    }

    /**
     * Updates the h value for a node
     *
     * @param node
     */
    public void updateHForNode(Node3d node) {
        if (node == target)
            setTarget(node);
        else if (target != null)
            node.setH(target);
    }

    /**
     * Generates a 2D grid.
     *
     * @param pos      Position
     * @param width    Horizontal nodes
     * @param height   Vertical nodes
     * @param distance Distance between nodes
     * @return An array with all the created nodes.
     */
    public Node3d[][] generate2DGrid(Point2d pos, int width, int height, double distance) {
        Node3d[][] array = new Node3d[width][height];

        // Create nodes
        allNodes.ensureCapacity(allNodes.size() + width * height);
        for (int x = 0; x < width; ++x) {
            double px = pos.x + x * distance;
            for (int y = 0; y < height; ++y) {
                array[x][y] = new Node3d(px, pos.y + y * distance, 0);
                allNodes.add(array[x][y]);
            }
        }

        // To avoid Math.sqrt inside the loops
        double diagonalDistance = Math.sqrt(2 * distance * distance);

        // Connect nodes to right/bottom
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (x + 1 < width) {
                    // horizontal
                    array[x][y].connectTo(array[x + 1][y], distance);
                    array[x + 1][y].connectTo(array[x][y], distance);

                    // Diagonal \
                    if (y + 1 < height) {
                        array[x][y].connectTo(array[x + 1][y + 1], diagonalDistance);
                        array[x + 1][y + 1].connectTo(array[x][y], diagonalDistance);
                    }
                    // Diagonal /
                    if (y > 0) {
                        array[x][y].connectTo(array[x + 1][y - 1], diagonalDistance);
                        array[x + 1][y - 1].connectTo(array[x][y], diagonalDistance);
                    }
                }
                // Vertical
                if (y + 1 < height) {
                    array[x][y].connectTo(array[x][y + 1], distance);
                    array[x][y + 1].connectTo(array[x][y], distance);
                }
            }
        }

        return array;
    }

    /**
     * Calls AStar to find a path and uptades the last path list.
     *
     * @return True if a path was found.
     */
    public boolean findPath() {
        long t = System.currentTimeMillis();
        allNodes.forEach(n -> n.setPredecessor(null));
        boolean ok = AStar.findPath(start, target);
        if (ok)
            updatePathList();
        else
            lastPath.clear();
        return ok;
    }

    /**
     * Updates the path list.
     * TODO remove?
     */
    public void updatePathList() {
        AStar.backtrackPath(lastPath, start, target);
    }

    /**
     * @return The current target
     */
    public Node3d getTarget() {
        return target;
    }

    /**
     * Sets a node as target and updates all h values.
     *
     * @param t The new target
     */
    public void setTarget(Node3d t) {
        target = t;
        if (t != null)
            getAllNodes().forEach(n -> n.setH(t));
    }

    /**
     * @return All nodes in this world
     */
    public ArrayList<Node3d> getAllNodes() {
        return allNodes;
    }

    /**
     * @return The current start
     */
    public Node3d getStart() {
        return start;
    }

    /**
     * Sets a node as start
     *
     * @param start The node
     */
    public void setStart(Node3d start) {
        this.start = start;
    }

    /**
     * @return If new nodes are automatically connected to all other nodes.
     */
    public boolean getAutoConnectToAll() {
        return autoConnectToAll;
    }

    /**
     * @param autoConnectToAll If new nodes should be automatically connected to all other nodes.
     */
    public void setAutoConnectToAll(boolean autoConnectToAll) {
        this.autoConnectToAll = autoConnectToAll;
    }
}
