package io.github.hannes99.world;

import io.github.hannes99.a_star.AStar;
import io.github.hannes99.a_star.Connection;
import io.github.hannes99.a_star.Node;

import javax.vecmath.Point3d;
import java.util.ArrayList;

/**
 * Manages all the nodes in a world.
 */
public class AStarWorld {
    private final ArrayList<Node3d> allNodes = new ArrayList<Node3d>();
    private final ArrayList<Node> lastPath = new ArrayList<Node>();
    private Node3d start, target;
    private boolean autoConnectToAll;
    private boolean autoUpdatePathList = true;

    public AStarWorld() {
        start = new Node3d(10, 10, 0);
        target = new Node3d(1000, 900, 0);
        allNodes.add(start);
        allNodes.add(target);
        generate2DGrid(400, 400, 50, 50, 2);
        // TODO auto add 2 nodes
    }

    public void destroyNode(Node3d node) {
        allNodes.remove(node);
        Connection toDelete;
        for(Connection c:node.getConnections()){
            toDelete=null;
            for(Connection cBack:c.getNode().getConnections()){
                if(cBack.getNode()==node){
                    toDelete=cBack;
                    continue;
                }
            }
            if(toDelete!=null)
                c.getNode().getConnections().remove(toDelete);

        }


        // TODO move to another function
        /*
        allNodes.forEach(n -> {
            for (int i = 0; i < n.getConnections().size(); ++i) {
                Connection c = n.getConnections().get(i);
                if (c.getNode() == node) {
                    n.getConnections().remove(c);
                    --i;
                }
            }
        });*/
    }

    public void destroyRadius(Point3d p, double radius) {
        //radius = radius;
        for (int i = 0; i < allNodes.size(); ++i) {
            Node3d node = allNodes.get(i);
            if (node.getPosition().distance(p) <= radius) {
                destroyNode(node);
                --i;
            }
        }
    }

    public Node3d createNode(double x, double y, double z) {
        Node3d node = new Node3d(x, y, z);
        allNodes.add(node);
        if (autoConnectToAll)
            connectToAll(node);
        node.setH(target);
        return node;
    }

    public void connectToAll(Node3d node) {
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
    public Node3d getNearestNode(Point3d p) {
        Node3d nearest = allNodes.get(0);
        double dist2 = nearest.getPosition().distanceSquared(p);
        for (Node3d node : allNodes) {
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

    // TODO return array so user knows all generated points?
    public void generate2DGrid(int bX, int bY, int offsetX, int offsetY, int space){
        Node3d[][] array = new Node3d[bX][bY];

        for(int y = 0;y<bY;y++){
            for(int x = 0;x<bX;x++){
                array[x][y] = new Node3d(offsetX + x * space, offsetY + y * space, 0);
                allNodes.add(array[x][y]);
            }
        }
        Node3d n;
        start = array[0][0]; //TODO remove
        target = array[bX-1][bY-1];
        for(int y = 0;y<bY;y++){
            for(int x = 0;x<bX;x++){
                n=array[x][y];
                if(y==0){
                    if(x==0){
                        n.connectTo(array[1][0]);
                        n.connectTo(array[1][1]);
                        n.connectTo(array[0][1]);
                    } else if(x==bX-1){
                        n.connectTo(array[x-1][0]);
                        n.connectTo(array[x-1][1]);
                        n.connectTo(array[x][1]);
                    } else {
                        n.connectTo(array[x-1][0]);
                        n.connectTo(array[x+1][0]);
                        n.connectTo(array[x-1][1]);
                        n.connectTo(array[x][1]);
                        n.connectTo(array[x+1][1]);
                    }
                } else if(y==bY-1){
                    if(x==0){
                        n.connectTo(array[x][y-1]);
                        n.connectTo(array[x+1][y-1]);
                        n.connectTo(array[x+1][y]);
                    } else if(x==bX-1){
                        n.connectTo(array[x-1][y]);
                        n.connectTo(array[x][y-1]);
                        n.connectTo(array[x-1][y-1]);
                    } else {
                        n.connectTo(array[x-1][y]);
                        n.connectTo(array[x+1][y]);
                        n.connectTo(array[x+1][y-1]);
                        n.connectTo(array[x-1][y-1]);
                        n.connectTo(array[x][y-1]);
                    }
                } else if(x==0){
                    if(y==0){
                        n.connectTo(array[1][0]);
                        n.connectTo(array[1][1]);
                        n.connectTo(array[0][1]);
                    } else if(y==bY-1){
                        n.connectTo(array[x][y-1]);
                        n.connectTo(array[x+1][y-1]);
                        n.connectTo(array[x+1][y]);
                    } else {
                        n.connectTo(array[x][y-1]);
                        n.connectTo(array[x][y+1]);
                        n.connectTo(array[x+1][y+1]);
                        n.connectTo(array[x+1][y]);
                        n.connectTo(array[x+1][y-1]);
                    }
                } else if(x==bX-1){
                    if(y==0){
                        n.connectTo(array[x-1][0]);
                        n.connectTo(array[x-1][1]);
                        n.connectTo(array[x][1]);
                    } else if(y==bY-1){
                        n.connectTo(array[x-1][y]);
                        n.connectTo(array[x][y-1]);
                        n.connectTo(array[x-1][y-1]);
                    } else {
                        n.connectTo(array[x][y+1]);
                        n.connectTo(array[x][y-1]);
                        n.connectTo(array[x-1][y+1]);
                        n.connectTo(array[x-1][y]);
                        n.connectTo(array[x-1][y-1]);
                    }
                } else {
                    n.connectTo(array[x-1][y-1]);
                    n.connectTo(array[x][y-1]);
                    n.connectTo(array[x+1][y-1]);
                    n.connectTo(array[x-1][y]);
                    n.connectTo(array[x+1][y]);
                    n.connectTo(array[x-1][y+1]);
                    n.connectTo(array[x][y+1]);
                    n.connectTo(array[x+1][y+1]);
                }
                array[x][y].setH(array[bX-1][bY-1]);
            }
        }


    }


    public void findPath() {
        long t = System.currentTimeMillis();
        allNodes.forEach(n -> n.setPredecessor(null));
        boolean ok = AStar.findPath(start, target);
        if (ok && autoUpdatePathList)
            updatePathList();
        else
            lastPath.clear();

        System.out.println("findPath: " + (System.currentTimeMillis() - t)); // TODO remove
    }

    public void updatePathList() {
        AStar.backtrackPath(lastPath, start, target);
    }

    public Node3d getTarget() {
        return target;
    }

    public ArrayList<Node3d> getAllNodes() {
        return allNodes;
    }

    public Node3d getStart() {
        return start;
    }

    public void setStart(Node3d start) {
        this.start = start;
    }

    public boolean getAutoConnectToAll() {
        return autoConnectToAll;
    }

    public void setAutoConnectToAll(boolean autoConnectToAll) {
        this.autoConnectToAll = autoConnectToAll;
    }
}
