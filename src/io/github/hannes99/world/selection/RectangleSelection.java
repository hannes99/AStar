package io.github.hannes99.world.selection;

import io.github.hannes99.world.Node3d;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by robert on 11/13/16.
 */
public class RectangleSelection extends Selection {
    protected Point2d pos1 = new Point2d();
    protected Point2d pos2 = new Point2d();

    public RectangleSelection() {
    }

    public RectangleSelection(AddArraySelection addArraySelection) {
        this();
        pos1.set(addArraySelection.pos1);
        pos2.set(addArraySelection.pos2);
    }

    @Override
    public void previousSelection(Selection selection) {
        if (selection instanceof RectangleSelection) {
            RectangleSelection s = (RectangleSelection) selection;
            pos1.set(s.pos1);
            pos2.set(s.pos2);
        }
    }

    @Override
    public ArrayList<Node3d> getSelectedNodes() {
        ArrayList<Node3d> ret = new ArrayList<Node3d>();

        for (Node3d n : worldRenderer.getWorld().getAllNodes()) {
            Point3d p = n.getPosition();
            if (p.x >= pos1.x && p.y >= pos1.y && p.x <= pos2.x && p.y <= pos2.y) { // TODO test
                ret.add(n);
            }
        }

        return ret;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        pos1.set(e.getX(), e.getY());
        pos2.set(pos1);
        worldRenderer.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        pos2.set(e.getX(), e.getY());
        if (pos2.x < pos1.x) { // TODO ????????
            double tmp = pos1.x;
            pos1.x = pos2.x;
            pos2.x = tmp;
        }
        if (pos2.y < pos1.y) {
            double tmp = pos1.y;
            pos1.y = pos2.y;
            pos2.y = tmp;
        }
        worldRenderer.repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(selectionColor);
        g.drawRect((int) pos1.x, (int) pos1.y, (int) (pos2.x - pos1.x), (int) (pos2.y - pos1.y));
        highlightNodes((Graphics2D) g);
    }
}
