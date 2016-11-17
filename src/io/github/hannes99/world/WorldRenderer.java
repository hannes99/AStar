package io.github.hannes99.world;

import io.github.hannes99.world.selection.*;

import javax.swing.*;
import javax.vecmath.Point3d;
import java.awt.*;

/**
 * Draws the state of the AStar Algorithm.
 */
public class WorldRenderer extends JComponent {
    private AStarWorld world;
    private Input mode;
    private double nodeRadius;
    private PathRenderer pathRenderer;
    private boolean renderPath;


    private double autoConnectDistance = 129, minDistance = 128;
    private Node3d lastCreatedNode;


    private Selection selection = new SingleSelection(this);

    public WorldRenderer(AStarWorld world, double nodeRadius) {
        setWorld(world);
        pathRenderer = new PathRenderer(world, this, nodeRadius);
        setNodeRadius(nodeRadius);
    }

    public AStarWorld getWorld() {
        return world;
    }

    public void setWorld(AStarWorld aStarWorld) {
        this.world = aStarWorld;
    }

    public void clear() {
        world.getAllNodes().clear();
        world.setStart(null);
        world.setTarget(null);
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

    public boolean getRenderPath() {
        return renderPath;
    }

    public void setRenderPath(boolean renderPath) {
        this.renderPath = renderPath;
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
        world.getAllNodes().forEach(n -> {
            Point3d p1 = n.getPosition();
            n.getConnections().forEach(c -> {
                Point3d p2 = ((Node3d) c.getNode()).getPosition();
                g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
            });
        });

        // All nodes
        world.getAllNodes().forEach(n -> {
            Point3d p1 = n.getPosition();
            g.setColor(new Color(230, 230, 230));
            g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
        });

        // Path
        if(renderPath)
            pathRenderer.paint(g);
        renderPath = false;

        // Highlight start an target nodes
        g.setColor(Color.CYAN);
        if (world.getStart() != null) {
            Point3d p1 = world.getStart().getPosition();
            g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
        }
        if (world.getTarget() != null) {
            Point3d p1 = world.getTarget().getPosition();
            g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
        }

        // Highlight selection
        if (selection != null) {
            selection.paint(g);
            g.setColor(new Color(0x0000FF));
            g.setStroke(new BasicStroke(2));
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
        repaint();
    }

    public void setInputMode(Input in) {
        mode = in;

        removeMouseListener(selection);
        removeMouseMotionListener(selection);
        selection = null;

        switch (mode) {
            case SelectSingle: {
                selection = new SingleSelection(this);
                break;
            }
            case SelectRectangle: {
                selection = new RectangleSelection(this);
                break;
            }
            case SelectCircle: {
                selection = new CircleSelection(this);
                break;
            }
            case AddNode: {
                selection = new AddNodeSelection(this);
                break;
            }
            case AddArray: {
                selection = new AddArraySelection(this);
                break;
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
        Node3d ret = world.getNearestNode(p);
        if (ret.getPosition().distanceSquared(p) > nodeRadius * nodeRadius)
            ret = null;
        return ret;
    }

    public PathRenderer getPathRenderer() {
        return pathRenderer;
    }

    public enum Input {
        SelectSingle, SelectRectangle, SelectCircle, AddNode, AddShape, AddArray
    }
}
