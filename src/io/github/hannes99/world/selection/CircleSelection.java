package io.github.hannes99.world.selection;

import io.github.hannes99.world.Node3d;

import javax.vecmath.Point3d;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Selects a circle.
 */
public class CircleSelection extends Selection {
    private Point3d pos = new Point3d();
    private double radius;

    @Override
    public void previousSelection(Selection selection) {
    }

    @Override
    public ArrayList<Node3d> getSelectedNodes() {
        ArrayList<Node3d> ret = new ArrayList<Node3d>();
        double radiusSquared = radius * radius;
        for (Node3d n : worldRenderer.getWorld().getAllNodes()) {
            if (n.getPosition().distanceSquared(pos) <= radiusSquared) {
                ret.add(n);
            }
        }
        return ret;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(selectionColor);
        g.drawOval((int) (pos.x - radius), (int) (pos.y - radius), (int) (radius * 2), (int) (radius * 2));
        highlightNodes((Graphics2D) g);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pos.set(e.getX(), e.getY(), 0);
        radius = 0;
        worldRenderer.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double dX = e.getX() - pos.getX();
        double dY = e.getY() - pos.getY();

        radius = Math.sqrt(dX * dX + dY * dY);
        worldRenderer.repaint();
    }
}
