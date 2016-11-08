package io.github.hannes99.a_star;

import java.util.ArrayList;

/**
 * Manages all the nodes in a world.
 */
public class AStarWorld {
    private ArrayList<Node> allNodes = new ArrayList<Node>();
    private Node start, target;
    private boolean autoConnectToAll;

    public AStarWorld() {
        // TODO auto add 2 nodes
    }

    public Node createNode(double x, double y) {
        Node node = new Node(x, y);
        allNodes.add(node);
        if (autoConnectToAll)
            connectToAll(node);
        return node;
    }

    public void connectToAll(Node node) {
        allNodes.forEach(n -> node.connectTo(n));
    }

    public Node getNearestNode(double x, double y) {
        // TODO after positions moved to vector class
    }

    public void setTarget() {
        // TODO
    }

    public Node getTarget() {
        return target;
    }

    public ArrayList<Node> getAllNodes() {
        return allNodes;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public boolean getAutoConnectToAll() {
        return autoConnectToAll;
    }

    public void setAutoConnectToAll(boolean autoConnectToAll) {
        this.autoConnectToAll = autoConnectToAll;
    }
}
