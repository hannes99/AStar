package io.github.hannes99.world.selection;

import io.github.hannes99.a_star.Node;
import io.github.hannes99.world.Node3d;

import java.awt.event.MouseEvent;

/**
 * Moves a single node. Destroys all connections and recreates them afterwards.
 */
public class MoveSingle extends SingleSelection {
    private Node3d node = null;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (node != null)
            node = null;
        else if (getSelectedNodes().size() == 1)
            node = getSelectedNodes().get(0);
    }

    @Override
    public void previousSelection(Selection selection) {
        super.previousSelection(selection);
        if (getSelectedNodes().size() == 1)
            node = selection.getSelectedNodes().get(0);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (node != null) {
            // Remove all connections
            for (Node n : node.getConnectionsFrom()) {
                n.removeConnectionTo(node);
            }
            node.getConnectionsTo().clear();

            // Set new position
            node.getPosition().setX(e.getX());
            node.getPosition().setY(e.getY());
            worldRenderer.getWorld().updateHForNode(node);

            // Recreate connections
            double radiusSquared = 20 * 20; // TODO
            for (Node3d n : worldRenderer.getWorld().getAllNodes()) { // TODO method in worldrenderer
                if (n.getPosition().distanceSquared(node.getPosition()) <= radiusSquared) {
                    node.connectTo(n);
                    n.connectTo(node);
                }
            }

            worldRenderer.repaint();
        }
    }
}
