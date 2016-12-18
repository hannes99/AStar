package io.github.hannes99.world.selection;

import io.github.hannes99.world.Node3d;

import java.awt.event.MouseEvent;

/**
 * Moves a single node.
 */
public class MoveSingle2 extends SingleSelection {
    private Node3d node;

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        if (node != null)
            node = null;
        else if (getSelectedNodes().size() == 1)
            node = getSelectedNodes().get(0);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (node != null) {
            pos.set(e.getX(), e.getY(), 0);
            node.getPosition().set(pos);
            worldRenderer.getWorld().updateHForNode(node);
            node.getConnectionsFrom().forEach(n -> {
                n.getConnectionsTo().forEach(c -> {
                    if (c.getNode() == node)
                        c.setValue(((Node3d) n).getPosition().distance(node.getPosition()));
                });
            });
            node.getConnectionsTo().forEach(c -> {
                c.setValue(node.getPosition().distance(((Node3d) c.getNode()).getPosition()));
            });
            worldRenderer.repaint();
        }
    }
}
