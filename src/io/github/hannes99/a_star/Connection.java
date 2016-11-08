package io.github.hannes99.a_star;

/**
 * Created by robert on 11/8/16.
 */
public class Connection {
    private Node node;
    private double value;

    public Connection(Node node, double value) {
        setNode(node);
        setValue(value);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
