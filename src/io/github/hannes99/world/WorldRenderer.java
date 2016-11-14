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
import java.util.ArrayList;

/**
 * Draws the state of the AStar Algorithm.
 */
public class WorldRenderer extends JComponent implements MouseInputListener, MouseWheelListener {
    private AStarWorld aStarWorld;
    private Input mode;
    private double nodeRadius;

    private Selection selection = new SingleSelection(aStarWorld, nodeRadius);

    public WorldRenderer(AStarWorld aStarWorld, double nodeRadius) {
        setWorld(aStarWorld);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        setNodeRadius(nodeRadius);
    }

    public Selection getSelection() {
        return selection;
    }

    public void setNodeRadius(double nodeRadius) {
        this.nodeRadius = nodeRadius;
    }

    @Override
    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.lightGray);
        aStarWorld.getAllNodes().forEach(n -> {
            Point3d p1 = n.getPosition();
            n.getConnections().forEach(c -> {
                Point3d p2 = ((Node3d) c.getNode()).getPosition();
                g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
            });
        });

        aStarWorld.getAllNodes().forEach(n -> {
            Point3d p1 = n.getPosition();
            if (n.getPredecessor() == null)
                g.setColor(new Color(230, 230, 230));
            else
                g.setColor(new Color(255, 100, 100));
            g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
        });


        g.setColor(new Color(50, 255, 50));
        if (aStarWorld.getLastPath() != null) {
            aStarWorld.getLastPath().forEach(node -> {
                Point3d p1 = ((Node3d) node).getPosition();
                g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
            });
        }


        g.setColor(Color.CYAN);
        Point3d p1 = aStarWorld.getStart().getPosition();
        g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
        p1 = aStarWorld.getTarget().getPosition();
        g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));

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

    public void setWorld(AStarWorld aStarWorld) {
        this.aStarWorld = aStarWorld;
    }

    public void setInputMode(Input in) {
        mode = in;

        removeMouseListener(selection);
        removeMouseMotionListener(selection);

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
            ret = null; // squared
        return ret;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ArrayList<Node3d> selectedList = null;
        if(selection!=null) {
            selectedList = selection.getSelectedNodes();
            switch (mode) {
                case SelectSingle: {
                    repaint();
                    break;
                }

                case Connect: {
                    for (int i = 0; i < selectedList.size(); i++) {
                        if (i + 1 < selectedList.size())
                            selectedList.get(i).connectTo(selectedList.get(i + 1));
                        selectedList.get(i + 1).connectTo(selectedList.get(i));
                    }
                    selectedList.clear();
                    break;
                }

                case ConnectAll: {
                    for (Node3d n : selectedList) {
                        for (Node3d n1 : selectedList)
                            if (n != n1)
                                n.connectTo(n1);
                    }
                    selectedList.clear();
                    break;
                }

                case AddNode: {
                    aStarWorld.createNode(e.getX(), e.getY(), 0);
                    break;
                }
            }
        }
        repaint();
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
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    public enum Input {
        SelectSingle, SelectRectangle, SelectCircle, AddNode, AddBox, AddCircle, Remove, Connect, ConnectAll
    }


}
