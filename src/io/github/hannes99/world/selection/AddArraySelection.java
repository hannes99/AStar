package io.github.hannes99.world.selection;

import io.github.hannes99.world.Node3d;

import javax.vecmath.Point2d;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by robert on 11/17/16.
 */
public class AddArraySelection extends RectangleSelection {
    private double distance = 64;
    private Node3d[][] nodes;

    public AddArraySelection() {
        selectionColor = Color.RED;
    }

    public AddArraySelection(RectangleSelection rectangleSelection) {
        this();
        pos1.set(rectangleSelection.pos1);
        pos2.set(rectangleSelection.pos2);
    }

    public void generateArray() {
        int w = (int) (pos2.x - pos1.x);
        int h = (int) (pos2.y - pos1.y);
        nodes = worldRenderer.getWorld().generate2DGrid(new Point2d(pos1), (int) (w / distance) + 1, (int) (h / distance) + 1, distance);
        worldRenderer.repaint();
    }

    @Override
    public ArrayList<Node3d> getSelectedNodes() {
        ArrayList<Node3d> ret = new ArrayList<Node3d>();
        if (nodes != null) {
            ret.ensureCapacity(nodes.length * nodes[0].length);
            for (int i = 0; i < nodes.length; ++i) {
                for (int j = 0; j < nodes[0].length; ++j) {
                    ret.add(nodes[i][j]);
                }
            }
        }
        return ret;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        for (int x = (int) pos1.x; x < pos2.x; x += distance) {
            g.drawLine(x, (int) pos1.y, x, (int) pos2.y);
        }
        for (int y = (int) pos1.y; y < pos2.y; y += distance) {
            g.drawLine((int) pos1.x, y, (int) pos2.x, y);
        }
        super.paint(g);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
        worldRenderer.repaint();
    }

    @Override
    public void highlightNodes(Graphics2D g) {
    }
}
