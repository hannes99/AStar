package io.github.hannes99.world.selection;

import io.github.hannes99.world.AStarWorld;
import io.github.hannes99.world.Node3d;

import java.awt.event.MouseEvent;

/**
 * Created by robert on 11/21/16.
 */
public class MoveSingle2 extends SingleSelection {

    @Override
    public void mouseDragged(MouseEvent e) {
        pos.set(e.getX(), e.getY(), 0);
        if (getSelectedNodes().size() != 0) {
            Node3d node = getSelectedNodes().get(0);
            node.getPosition().set(pos);
            AStarWorld world = worldRenderer.getWorld();
            if (node == world.getTarget())
                world.setTarget(node);
            else
                node.setH(world.getTarget());
            worldRenderer.repaint();
        }
    }
}
