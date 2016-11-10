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
    private ArrayList<Node> lastPath;
    private boolean autoUpdatePathList = true;

    public AStarWorld() {
        start = new Node(100, 100, 0);
        target = new Node(600, 600, 0);
        allNodes.add(start);
        allNodes.add(target);


        for (int i = 0; i < 1024; i += 2) {
            for (int j = 0; j < 768; j += 2) {
                connectToAll(createNode(i, j, 0));
            }
        }

        // TODO auto add 2 nodes

    }

    public void destroyNode(Node node) {
        allNodes.remove(node);
        allNodes.forEach(n -> {
            n.getConnections().forEach(c -> {
                if (c.getNode() == node) {
                    n.getConnections().remove(c); // TODO alksddfölasjlödf
                }
            });
        });
    }

    public Node createNode(double x, double y, double z) {
        Node node = new Node(x, y, z);
        allNodes.add(node);
        if (autoConnectToAll)
            connectToAll(node);
        node.setH(target);
        return node;
    }

    public void connectToAll(Node node) {
        allNodes.forEach(n -> {
            double distance = node.getPosition().distance(n.getPosition());
            if (distance < 3) { // TODO remove if
                node.connectTo(n);
                n.connectTo(node);
            }
        });

    }

    public boolean isAutoUpdatePathList() {
        return autoUpdatePathList;
    }

    public void setAutoUpdatePathList(boolean autoUpdatePathList) {
        this.autoUpdatePathList = autoUpdatePathList;
    }

    public ArrayList<Node> getLastPath() {
        return lastPath;
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
        // TODO update all h values and set target
    }


    public void findPath() {
        long t = System.currentTimeMillis();
        allNodes.forEach(n -> n.setPredecessor(null));
        boolean ok = AStar.findPath(start, target);
        if (ok && autoUpdatePathList)
            updatePathList();

        System.out.println("findPath: " + (System.currentTimeMillis() - t)); // TODO remove
    }

    public void updatePathList() {
        lastPath = AStar.backtrackPath(start, target);
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
