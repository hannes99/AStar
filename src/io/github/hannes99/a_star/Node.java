package io.github.hannes99.a_star;

import java.util.ArrayList;

/**
 * A node with connections to other nodes.
 */
public class Node {
    // Extra
    Object extra; // Can be used to store data
    // Position
    private double x, y;
    // AStar
    private double g, h; // G and H function values
    private Node predecessor; // To store the shortest (known) way to start
    private ArrayList<Connection> connections = new ArrayList<Connection>();

    /**
     * Creates a new node
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public Node(int x, int y) {
        setPosition(x, y);
    }

    /**
     * @return G
     */
    public double getG() {
        return g;
    }

    /**
     *
     * @param g New value for G
     */
    public void setG(double g) {
        this.g = g;
    }

    /**
     * Sets the Position
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return F
     */
    public double getF() {
        return getG() + getH();
    }

    /**
     *
     * @return H
     */
    public double getH() {
        return h;
    }

    /**
     * Sets the distance to the target node as H
     *
     * @param target The target node
     */
    public void setH(Node target) {
        double deltaX = getX() - target.getX();
        double deltaY = getY() - target.getY();
        setH(Math.sqrt(deltaX * deltaX + deltaY * deltaY));
    }

    /**
     *
     * @param value New value vor H
     */
    public void setH(double value) {
        h = value;
    }

    /**
     *
     * @return X coordinate
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return Y coordinate
     */
    public double getY() {
        return y;
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
     *
     * @param previousNode Better way to start
     */
    public void setPredecessor(Node previousNode) {
        this.predecessor = previousNode;
    }

    /**
     *
     * @return All connections from this node
     */
    public ArrayList<Connection> getConnections() {
        return connections;
    }
}
