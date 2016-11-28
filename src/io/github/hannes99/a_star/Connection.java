package io.github.hannes99.a_star;

/**
 * Represents a connection to another Node
 */
public class Connection {
    private Node node;
    private double value;

    /**
     * Creates a new connection
     *
     * @param node  The connected node
     * @param value The cost of this connection
     */
    public Connection(Node node, double value) {
        setNode(node);
        setValue(value);
    }

    /**
     *
     * @return The cost of this connection
     */
    public double getValue() {
        return value;
    }

    /**
     *
     * @param value The cost of this connection
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     *
     * @return The connected node
     */
    public Node getNode() {
        return node;
    }

    /**
     *
     * @param node The connected node
     */
    public void setNode(Node node) {
        this.node = node;
    }
}
