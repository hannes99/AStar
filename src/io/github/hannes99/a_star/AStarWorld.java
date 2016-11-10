package io.github.hannes99.a_star;

import javax.vecmath.Point3d;
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

    public Node createNode(double x, double y, double z) {
        Node node = new Node(x, y, z);
        allNodes.add(node);
        if (autoConnectToAll)
            connectToAll(node);
        return node;
    }

    public void connectToAll(Node node) {
        allNodes.forEach(n -> node.connectTo(n));
    }

    /**
     * @param p A position
     * @return The closest node to p
     */
    public Node getNearestNode(Point3d p) {
        Node nearest = allNodes.get(0);
        double dist2 = nearest.getPosition().distanceSquared(p);
        for (Node node : allNodes) {
            double d = node.getPosition().distanceSquared(p);
            if (d < dist2) {
                dist2 = d;
                nearest = node;
            }
        }
        return nearest;
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
