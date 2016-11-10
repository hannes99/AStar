package io.github.hannes99.gui.debug_renderer;

import io.github.hannes99.a_star.AStarWorld;
import io.github.hannes99.a_star.Node;

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

    private Node selected;

    public WorldRenderer(AStarWorld aStarWorld, double nodeRadius) {
        setWorld(aStarWorld);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        setNodeRadius(nodeRadius);
    }

    public void setNodeRadius(double nodeRadius) {
        this.nodeRadius = nodeRadius;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        aStarWorld.getAllNodes().forEach(n -> {
            Point3d p1 = n.getPosition();
            /*
            g.setColor(Color.YELLOW);
            n.getConnections().forEach(c -> {
                Point3d p2 = c.getNode().getPosition();
                g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
            });*/
            if (n.getPredecessor() == null)
                g.setColor(Color.RED);
            else
                g.setColor(Color.BLUE);
            g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
        });


        g.setColor(Color.GREEN);
        if (aStarWorld.getLastPath() != null) {
            aStarWorld.getLastPath().forEach(node -> {
                Point3d p1 = node.getPosition();
                g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
            });
        }


        g.setColor(Color.CYAN);
        Point3d p1 = aStarWorld.getStart().getPosition();
        g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
        p1 = aStarWorld.getTarget().getPosition();
        g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));

    }

    public void setWorld(AStarWorld aStarWorld) {
        this.aStarWorld = aStarWorld;
    }

    public void setInputMode(Input im) {
        mode = im;
    }

    /**
     * @param x X position
     * @param y Y position
     * @return The clicked node or null
     */
    public Node getClickedNode(double x, double y) {
        Point3d p = new Point3d(x, y, 0);
        Node ret = aStarWorld.getNearestNode(p);
        if (ret.getPosition().distanceSquared(p) > nodeRadius * nodeRadius)
            ret = null; // squared
        return ret;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //aStarWorld.createNode(e.getX(), e.getY(), 0);
        switch (mode) {
            case Select: {
                selected = getClickedNode(e.getX(), e.getY());
                break;
            }
            case AddNode: {
                aStarWorld.createNode(e.getX(), e.getY(), 0);
                break;
            }
            case RemoveRadius: {
                aStarWorld.destroyRadius(new Point3d(e.getX(), e.getY(), 0), 50);
                break;
            }
        }
        System.out.println("Doen!");
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public enum Input {
        Select, AddNode, AddBox, AddCircle, RemoveRadius
    }


}
