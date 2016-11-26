package io.github.hannes99.world.selection;

import io.github.hannes99.world.Node3d;

import java.awt.event.MouseEvent;

/**
 * Created by robert on 11/21/16.
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
            if (getSelectedNodes().size() != 0) {
                Node3d node = getSelectedNodes().get(0);
                node.getPosition().set(pos);
                worldRenderer.getWorld().updateHForNode(node);
                worldRenderer.repaint();
            }
        }
    }
}
