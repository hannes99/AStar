package io.github.hannes99.world.selection;

import io.github.hannes99.world.Node3d;

import javax.vecmath.Point3d;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Selects a single node
 */
public class SingleSelection extends Selection {
    public Point3d pos = new Point3d();

    @Override
    public void previousSelection(Selection selection) {
        if (selection instanceof SingleSelection) {
            SingleSelection s = (SingleSelection) selection;
            pos.set(s.pos);
        }
    }

    @Override
    public ArrayList<Node3d> getSelectedNodes() {
        ArrayList<Node3d> nodes = new ArrayList<Node3d>();
        Node3d selected = worldRenderer.getWorld().getNearestNode(pos);
        double nodeRadius = worldRenderer.getNodeRadius();
        if (selected != null && selected.getPosition().distanceSquared(pos) <= nodeRadius * nodeRadius)
            nodes.add(selected);
        return nodes;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pos.set(e.getX(), e.getY(), 0);
        worldRenderer.repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(selectionColor);
        highlightNodes((Graphics2D) g);
    }
}
