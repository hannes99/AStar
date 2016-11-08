package io.github.hannes99.a_star;

import java.util.ArrayList;

/**
 * Created by robert on 11/8/16.
 */
public class Node {
    private double g;
    private ArrayList<Connection> connections = new ArrayList<Connection>();

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }
}
