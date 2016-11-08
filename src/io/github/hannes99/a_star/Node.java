package io.github.hannes99.a_star;

import java.util.ArrayList;

/**
 * Created by robert on 11/8/16.
 */
public class Node {
    private double g, h;
    private Node predecessor;
    private ArrayList<Connection> connections = new ArrayList<Connection>();

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getF() {
        return getG() + getH();
    }

    public double getH() {
        return h;
    }

    /**
     * Sets H to a specific Value
     *
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
        // TODO setH(distance);
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Node previousNode) {
        this.predecessor = previousNode;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }
}
