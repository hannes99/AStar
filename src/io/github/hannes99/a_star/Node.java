package io.github.hannes99.a_star;

import java.util.ArrayList;

/**
 * A node with connections to other nodes.
 */
public abstract class Node {
    private double g, h; // G and H function values
    private Node predecessor; // To store the shortest (known) way to start
    private ArrayList<Connection> connectionsTo = new ArrayList<Connection>();
    private ArrayList<Node> connectionsFrom = new ArrayList<Node>();

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
     * @return F
     */
    public double getF() {
        return getG() + getH();
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
     * @param previousNode Set a better way to the start node
     */
    public void setPredecessor(Node previousNode) {
        this.predecessor = previousNode;
    }

    /**
     * Nodes which are connected to this
     * @return
     */
    public ArrayList<Node> getConnectionsFrom() {
        return connectionsFrom;
    }

    /**
     * @return All connections from this node
     */
    public ArrayList<Connection> getConnectionsTo() {
        return connectionsTo;
    }

    /**
     * Adds a connection to the node
     *
     * @param target The target node
     * @param value  Value of this connection
     */
    public Connection connectTo(Node target, double value) {
        Connection connection = null;
        if (target != null && target != this) {
            boolean exists = false;
            for (int i = 0; i < connectionsTo.size() && !exists; ++i)
                exists = connectionsTo.get(i).getNode() == target;
            if (!exists) {
                connection = new Connection(target, value);
                connectionsTo.add(connection);
                target.getConnectionsFrom().add(this);
            }
        }
        return connection;
    }

}
