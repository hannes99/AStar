package io.github.hannes99.a_star;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Imlementation of the A* Algorithm
 */
public class AStar {
    // Nodes with possible next nodes
    public static final PriorityQueue<Node> openlist = new PriorityQueue<>((o1, o2) -> {
        int ret = 0;
        if (o1.getF() < o2.getF())
            ret = -1;
        else if (o1.getF() > o2.getF())
            ret = 1;
        return ret;
    });

    // Path known
    public static final HashSet<Node> closedList = new HashSet<>();


    /**
     * This class should not be instantiated
     */
    private AStar() {
    }

    /**
     * Calculates the path. All precedessors need to be null
     *
     * @param start Start node
     * @param end   End node
     * @return If the path was found
     */
    public static boolean findPath(Node start, Node end) {
        return findPath(start, end, 0);
    }


    /**
     * Calculates the path. All precedessors need to be null. Terminates if the path was found,
     * if there is no way or if steps reaches maxSteps.
     *
     * @param start    Start node
     * @param end      End node
     * @param maxSteps Maximum number of steps, 0 = no limit
     * @return If the path was found
     */
    public static boolean findPath(Node start, Node end, long maxSteps) {
        boolean pathFound = false;
        // Validate input
        if (start != null && end != null) {
            // Prepare Lists
            openlist.clear();
            openlist.add(start);
            closedList.clear();

            // Loop until
            // - solution found
            // - no possible solution
            long steps = 0;
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
                    ++steps;
                }
            } while (!openlist.isEmpty() && !pathFound && (maxSteps == 0 || steps != maxSteps));
        }

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
        currendNode.getConnectionsTo().forEach(successor -> {
            // Check closed list
            if (!closedlist.contains(successor.getNode())) {
                // Tentative cost
                double tentativeG = currendNode.getG() + successor.getValue();
                // If the successor is already in the openlist, but the new way is worse than
                // the old one there is nothing to do.
                // Otherwise:
                if (!(openlist.contains(successor.getNode()) && tentativeG >= successor.getNode().getG())) {
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
     * @param ret   The list to fill
     * @param start Starting node of the path
     * @param end   Last node
     * @return The list for chaining
     */
    public static ArrayList<Node> backtrackPath(ArrayList<Node> ret, Node start, Node end) {
        ret.clear();
        Node node = end;
        ret.add(node);
        while (node != start) {
            node = node.getPredecessor();
            ret.add(node);
        }
        return ret;
    }

}
