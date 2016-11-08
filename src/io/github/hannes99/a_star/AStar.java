package io.github.hannes99.a_star;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Imlementation of the A* Algorithm
 */
public class AStar {

    /**
     * This class should not be instantiated
     */
    private AStar() {
    }

    /**
     * Calculates the path
     *
     * @param start Start node
     * @param end   End node
     * @return If the path was found
     */
    public static boolean findPath(Node start, Node end) {
        // Validate input
        if (start == null || end == null)
            throw new IllegalArgumentException("null");

        // Nodes with possible next nodes
        PriorityQueue<Node> openlist = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                int ret = 0;
                if (o1.getF() < o2.getF())
                    ret = -1;
                else if (o1.getF() > o2.getF())
                    ret = 1;
                return ret;
            }
        });
        openlist.add(start);

        // Path known
        HashSet<Node> closedList = new HashSet<Node>();

        // Loop until
        // - solution found
        // - no possible solution
        boolean pathFound = false;
        do {
            // Node with the lowes f
            Node currentNode = openlist.poll();

            // Path found
            pathFound = currentNode == end;

            if (!pathFound) {
                // This node shouldn't be checked again to prevent loops
                closedList.add(currentNode);

                // Add connected nodes
                expandNode(currentNode, openlist, closedList);
            }
        } while (!openlist.isEmpty() && !pathFound);

        // Return if the path was found
        return pathFound;
    }

    /**
     * Adds connected nodes if
     * - node appears for the first time
     * - better way to node found
     *
     * @param currendNode Currend Node
     * @param openlist    Openlist
     * @param closedlist  Closedlist
     */
    private static void expandNode(Node currendNode, PriorityQueue<Node> openlist, HashSet<Node> closedlist) {
        currendNode.getConnections().forEach(successor -> {
            // Check closed list
            if (!closedlist.contains(successor.getNode())) {
                // Tentative cost
                double tentativeG = currendNode.getG() + successor.getValue();
                // If the successor is already in the openlist, but the new way is worse than
                // the old one there is nothing to do.
                // Otherwise:
                if (!(openlist.contains(successor.getNode()) && tentativeG >= successor.getValue())) {
                    // Set previous node
                    successor.getNode().setPredecessor(currendNode);
                    // Set or update g
                    successor.getNode().setG(tentativeG);
                    // Add node to openlist
                    if (!openlist.contains(successor.getNode())) {
                        openlist.add(successor.getNode());
                    }
                }
            }
        });
    }

    /**
     * Fills an ArrayList with the Nodes from end to start (the first element will be end).
     * This method can only work after findPath was called.
     *
     * @param start Starting node of the path
     * @param end   Last node
     * @return All the nodes sorted from end to start
     */
    public static ArrayList<Node> backtrackPath(Node start, Node end) {
        ArrayList<Node> ret = new ArrayList<Node>();
        Node node = end;
        ret.add(node);
        while (node != start) {
            node = node.getPredecessor();
            ret.add(node);
        }
        return ret;
    }


}
