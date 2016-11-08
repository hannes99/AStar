package io.github.hannes99.a_star;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by robert on 11/8/16.
 */
public class AStar {
    /**
     * Calculates the path
     *
     * @param start Start node
     * @param end   End node
     * @return The path or null
     */
    public static ArrayList<Node> findPath(Node start, Node end) {
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
        start.setF(0);
        openlist.add(start);

        // Path known
        HashSet<Node> closedList = new HashSet<Node>();

        // Loop until
        // - solution found
        // - no possible solution
        do {
            // Node with the lowes f
            Node currentNode = openlist.poll();

            // Path found
            if (currentNode == end)
                return asddf;

            // This node shouldn't be checked again to prevent loops
            closedList.add(currentNode);

            // Add connected nodes
            expandNode(currentNode, openlist, closedList);
        } while (!openlist.isEmpty());

        // Path not found
        return null;
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
        currendNode.getConnections().forEach(c -> {
            // Not in closed list
            if (!closedlist.contains(c.getNode())) {
                // Tentative cost
                double tentativeG = currendNode.getG() + c.getValue();

                // TODO
            }
        });
    }


}
