package io.github.hannes99.world.selection;

import io.github.hannes99.world.AStarWorld;
import io.github.hannes99.world.Node3d;
import io.github.hannes99.world.WorldRenderer;

import javax.vecmath.Point3d;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by robert on 11/17/16.
 */
public class AddNodeSelection extends SingleSelection {
    private Node3d lastCreatedNode;

    public AddNodeSelection(WorldRenderer worldRenderer) {
        super(worldRenderer);
    }

    private void addNode(int x, int y) {
        Point3d p = new Point3d(x, y, 0);
        AStarWorld world = worldRenderer.getWorld();
        if (lastCreatedNode == null || lastCreatedNode.getPosition().distance(p) >= worldRenderer.getMinDistance()) {
            Node3d n = world.createNode(p.x, p.y, 0);
            double autoConnectDistance2 = worldRenderer.getAutoConnectDistance() * worldRenderer.getAutoConnectDistance();
            world.getAllNodes().forEach(node -> {
                if (node.getPosition().distanceSquared(p) <= autoConnectDistance2) {
                    n.connectTo(node);
                    node.connectTo(n);
                }
            });
            lastCreatedNode = n;
            worldRenderer.repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (lastCreatedNode != null) {
            Point3d p = lastCreatedNode.getPosition();
            g.setColor(Color.MAGENTA);
            double minDistance = worldRenderer.getMinDistance();
            double autoConnectDistance = worldRenderer.getAutoConnectDistance();
            g.drawOval((int) (p.x - minDistance), (int) (p.y - minDistance), (int) (minDistance * 2), (int) (minDistance * 2));
            g.setColor(Color.RED);
            g.drawOval((int) (p.x - autoConnectDistance), (int) (p.y - autoConnectDistance), (int) (autoConnectDistance * 2), (int) (autoConnectDistance * 2));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        addNode(e.getX(), e.getY());
        worldRenderer.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        // TODO max distance begrenzen
        addNode(e.getX(), e.getY());
        worldRenderer.repaint();
    }
}
