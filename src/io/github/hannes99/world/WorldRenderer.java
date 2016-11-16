package io.github.hannes99.world;

import io.github.hannes99.world.selection.CircleSelection;
import io.github.hannes99.world.selection.RectangleSelection;
import io.github.hannes99.world.selection.Selection;
import io.github.hannes99.world.selection.SingleSelection;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.vecmath.Point3d;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Draws the state of the AStar Algorithm.
 */
public class WorldRenderer extends JComponent implements MouseInputListener, MouseWheelListener {
    private AStarWorld aStarWorld;
    private Input mode;
    private double nodeRadius;
    private PathRenderer pathRenderer;

    private double autoConnectDistance = 129, minDistance = 128;
    private Node3d lastCreatedNode;


    private Selection selection = new SingleSelection(aStarWorld, nodeRadius);

    public WorldRenderer(AStarWorld aStarWorld, double nodeRadius) {
        setWorld(aStarWorld);
        pathRenderer = new PathRenderer(aStarWorld, this, nodeRadius);
        //Mouselisteners setzen
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        setNodeRadius(nodeRadius);
    }

    public AStarWorld getWorld() {
        return aStarWorld;
    }

    public void setWorld(AStarWorld aStarWorld) {
        this.aStarWorld = aStarWorld;
    }

    public void clear() {
        aStarWorld.getAllNodes().clear();
        aStarWorld.setStart(null);
        aStarWorld.setTarget(null);
        lastCreatedNode = null;
        repaint();
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
        repaint();
    }

    public double getAutoConnectDistance() {
        return autoConnectDistance;
    }

    public void setAutoConnectDistance(double autoConnectDistance) {
        this.autoConnectDistance = autoConnectDistance;
        repaint();
    }

    public Selection getSelection() {
        return selection;
    }

    @Override
    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());


        // Connections
        g.setColor(Color.lightGray);
        aStarWorld.getAllNodes().forEach(n -> {
            Point3d p1 = n.getPosition();
            n.getConnections().forEach(c -> {
                Point3d p2 = ((Node3d) c.getNode()).getPosition();
                g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
            });
        });

        // All nodes
        aStarWorld.getAllNodes().forEach(n -> {
            Point3d p1 = n.getPosition();
            g.setColor(new Color(230, 230, 230));
            g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
        });

        // Path
        pathRenderer.paint(g);

        // Min and auto connect distance
        if (mode == Input.AddNode && lastCreatedNode != null) {
            Point3d p = lastCreatedNode.getPosition();
            g.setColor(Color.MAGENTA);
            g.drawOval((int) (p.x - minDistance), (int) (p.y - minDistance), (int) (minDistance * 2), (int) (minDistance * 2));
            g.setColor(Color.RED);
            g.drawOval((int) (p.x - autoConnectDistance), (int) (p.y - autoConnectDistance), (int) (autoConnectDistance * 2), (int) (autoConnectDistance * 2));
        }

        // Highlight selection
        if (selection != null) {
            g.setColor(new Color(0x0000FF));
            g.setStroke(new BasicStroke(2));
            selection.paint(g);
            for (Node3d n : selection.getSelectedNodes()) {
                Point3d p = n.getPosition();
                g.drawOval((int) (p.x - nodeRadius), (int) (p.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
            }

        }
    }

    public double getNodeRadius() {
        return nodeRadius;
    }

    public void setNodeRadius(double nodeRadius) {
        this.nodeRadius = nodeRadius;
        pathRenderer.setRadius(nodeRadius);
        if (selection instanceof SingleSelection)
            ((SingleSelection) selection).setNodeRadius(nodeRadius);
    }

    public void setInputMode(Input in) {
        mode = in;

        removeMouseListener(selection);
        removeMouseMotionListener(selection);
        selection = null;

        switch (mode) {
            case SelectSingle: {
                selection = new SingleSelection(aStarWorld, nodeRadius);
                break;
            }
            case SelectRectangle: {
                selection = new RectangleSelection(aStarWorld);
                break;
            }
            case SelectCircle: {
                selection = new CircleSelection(aStarWorld);
            }
        }

        addMouseListener(selection);
        addMouseMotionListener(selection);
    }

    /**
     * @param x X position
     * @param y Y position
     * @return The clicked node or null
     */
    public Node3d getClickedNode(double x, double y) {
        Point3d p = new Point3d(x, y, 0);
        Node3d ret = aStarWorld.getNearestNode(p);
        if (ret.getPosition().distanceSquared(p) > nodeRadius * nodeRadius)
            ret = null;
        return ret;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (mode) {
            case SelectSingle: {
                repaint();
                break;
            }
            case AddNode: {
                addNode(e.getX(), e.getY());
                break;
            }
        }
    }

    private void addNode(int x, int y) {
        Point3d p = new Point3d(x, y, 0);
        if (lastCreatedNode == null || lastCreatedNode.getPosition().distance(p) >= minDistance) {
            Node3d n = aStarWorld.createNode(p.x, p.y, 0);
            aStarWorld.getAllNodes().forEach(node -> {
                if (node.getPosition().distanceSquared(p) <= autoConnectDistance * autoConnectDistance) {
                    n.connectTo(node);
                    node.connectTo(n);
                }
            });
            lastCreatedNode = n;
            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (mode) {
            case SelectCircle:
            case SelectRectangle: {
                repaint();
                break;
            }
            case AddNode: {
                addNode(e.getX(), e.getY());
                break;
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public PathRenderer getPathRenderer() {
        return pathRenderer;
    }

    public enum Input {
        SelectSingle, SelectRectangle, SelectCircle, AddNode, AddShape, AddArray
    }
}
