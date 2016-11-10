package io.github.hannes99.a_star;

import javax.vecmath.Point3d;
import java.util.ArrayList;


/**
 * A node with connections to other nodes.
 */
public class Node {
    // Position
    final Point3d position = new Point3d();
    // Extra
    Object extra; // Can be used to store data
    // AStar
    private double g, h; // G and H function values
    private Node predecessor; // To store the shortest (known) way to start
    private ArrayList<Connection> connections = new ArrayList<Connection>();

    /**
     * Creates a new node
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param z The Z coordinate
     */
    public Node(double x, double y, double z) {
        position.set(x, y, z);
    }

    /**
     * @return G
     */
    public double getG() {
        return g;
    }

    /**
     * @param g New value for G
     */
    public void setG(double g) {
        this.g = g;
    }


    /**
     *
     * @return The position
     */
    public Point3d getPosition() {
        return position;
    }

    /**
     * @return F
     */
    public double getF() {
        return getG() + getH();
    }

    /**
     * @return H
     */
    public double getH() {
        return h;
    }

    /**
     * @param value New value vor H
     */
    public void setH(double value) {
        h = value;
    }

    /**
     * Sets the distance to the target node as H
     *
     * @param target The target node
     */
    public void setH(Node target) {
        setH(target.getPosition().distance(position));
    }

    /**
     * @return User data
     */
    public Object getExtra() {
        return extra;
    }

    /**
     * Can be used to store data related to this node.
     *
     * @param extra User data
     */
    public void setExtra(Object extra) {
        this.extra = extra;
    }

    /**
     * Stores the shortest known way to start.
     *
     * @return The previous node
     */
    public Node getPredecessor() {
        return predecessor;
    }

    /**
     * @param previousNode Better way to start
     */
    public void setPredecessor(Node previousNode) {
        this.predecessor = previousNode;
    }

    /**
     * @return All connections from this node
     */
    public ArrayList<Connection> getConnections() {
        return connections;
    }

    /**
     * Adds a connection to the node
     *
     * @param target The target node
     * @param value  Value of this connection
     */
    public Connection connectTo(Node target, double value) {
        Connection connection = null;
        boolean exists = false;
        for(Connection c:connections)
            if(c.getNode()==target)
                exists = true;
        if (!exists && target != null && target != this) {
            connection = new Connection(target, value);
            connections.add(connection);
        }
        return connection;
    }

    /**
     * Connects to a node and sets the distance as value
     *
     * @param target The target node
     */
    public Connection connectTo(Node target) {
        return connectTo(target, target.getPosition().distance(position));
    }

}
