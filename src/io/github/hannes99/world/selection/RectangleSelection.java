package io.github.hannes99.world.selection;

import io.github.hannes99.world.AStarWorld;
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
    private Point2d pos1 = new Point2d();
    private Point2d pos2 = new Point2d();

    public RectangleSelection(AStarWorld world) {
        super(world);
    }

    @Override
    public ArrayList<Node3d> getSelectedNodes() {
        ArrayList<Node3d> ret = new ArrayList<Node3d>();

        for (Node3d n : world.getAllNodes()) {
            Point3d p = n.getPosition();
            if (p.x >= pos1.x && p.y >= pos1.y && p.x <= pos2.x && p.y <= pos2.y) { // TODO test
                ret.add(n);
            }
        }

        return ret;
    }

    @Override
    public int removeSelectedNodes() {
        ArrayList<Node3d> nodes = getSelectedNodes();
        int ret = nodes.size();
        for (Node3d n : nodes) {
            world.destroyNode(n);
        }
        return ret;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pos1.set(e.getX(), e.getY());
        pos2.set(pos1);
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
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(selectionColor);
        g.drawRect((int) pos1.x, (int) pos1.y, (int) (pos2.x - pos1.x), (int) (pos2.y - pos1.y));
    }
}
