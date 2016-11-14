package io.github.hannes99.world.selection;

import io.github.hannes99.world.AStarWorld;
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
    private double nodeRadius;

    public SingleSelection(AStarWorld world, double nodeRadius) {
        super(world);
        this.nodeRadius = nodeRadius;
    }

    @Override
    public ArrayList<Node3d> getSelectedNodes() {
        ArrayList<Node3d> nodes = new ArrayList<Node3d>();
        Node3d selected = world.getNearestNode(pos);
        if (selected != null && selected.getPosition().distanceSquared(pos) <= nodeRadius * nodeRadius)
            nodes.add(selected);
        return nodes;
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
        pos.set(e.getX(), e.getY(), 0);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(selectionColor);
        g.drawOval((int) (pos.x - nodeRadius), (int) (pos.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
    }
}
