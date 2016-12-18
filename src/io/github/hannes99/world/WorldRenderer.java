package io.github.hannes99.world;

import io.github.hannes99.world.selection.Selection;

import javax.swing.*;
import javax.vecmath.Point3d;
import java.awt.*;

/**
 * Draws the state of the AStar World and Algorithm.
 */
public class WorldRenderer extends JComponent {
    private AStarWorld world;
    private double nodeRadius;
    private PathRenderer pathRenderer;
    private boolean renderPath, drawValues;

    private boolean drawNodes, drawConnections;

    private double autoConnectDistance = 129, minDistance = 128;
    private Selection selection;

    /**
     * Creates a new WorldRenderer.
     *
     * @param world      The world to render.
     * @param nodeRadius Size of the nodes.
     */
    public WorldRenderer(AStarWorld world, double nodeRadius) {
        setWorld(world);
        pathRenderer = new PathRenderer(world, this, nodeRadius);
        setNodeRadius(nodeRadius);
        drawConnections = true;
        drawNodes = true;
    }

    /**
     * Should text values be drawn. Repaints afterwards.
     *
     * @param b Should text values be drawn.
     */
    public void setDrawValues(boolean b) {
        drawValues = b;
        repaint();
    }

    /**
     *
     * @return The world
     */
    public AStarWorld getWorld() {
        return world;
    }

    /**
     *
     * @param aStarWorld A world
     */
    public void setWorld(AStarWorld aStarWorld) {
        this.world = aStarWorld;
    }

    /**
     * Resets the world.
     */
    public void clear() {
        world.getAllNodes().clear();
        world.setStart(null);
        world.setTarget(null);

        repaint();
    }

    /**
     * Min distance when creating single nodes.
     *
     * @return the distance
     */
    public double getMinDistance() {
        return minDistance;
    }

    /**
     * Min distance when creating single nodes.
     *
     * @param minDistance the distance
     */
    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
        repaint();
    }

    /**
     *
     * @return if connecitons are visible
     */
    public boolean getDrawConnections() {
        return drawConnections;
    }

    /**
     * Should connections be drawn? Repaints afterwards.
     *
     * @param drawConnections draw connections
     */
    public void setDrawConnections(boolean drawConnections) {
        this.drawConnections = drawConnections;
        repaint();
    }

    /**
     * @return If nodes are visible.
     */
    public boolean getDrawNodes() {
        return drawNodes;
    }

    /**
     * Set if nodes should be drawn. Repaints afterwards.
     *
     * @param drawNodes draw nodes
     */
    public void setDrawNodes(boolean drawNodes) {
        this.drawNodes = drawNodes;
        repaint();
    }

    /**
     * Distance for auto connections
     *
     * @return the distance
     */
    public double getAutoConnectDistance() {
        return autoConnectDistance;
    }

    /**
     * Distance for auto connections
     *
     * @param autoConnectDistance the distance
     */
    public void setAutoConnectDistance(double autoConnectDistance) {
        this.autoConnectDistance = autoConnectDistance;
        repaint();
    }

    /**
     *
     * @return Is the algorithm visible
     */
    public boolean getRenderPath() {
        return renderPath;
    }

    /**
     * Should the algorithm be drawn
     *
     * @param renderPath render path
     */
    public void setRenderPath(boolean renderPath) {
        this.renderPath = renderPath;
    }

    /**
     *
     * @return current selection
     */
    public Selection getSelection() {
        return selection;
    }

    /**
     * Set a new selection
     *
     * @param s the selection
     */
    public void setSelection(Selection s) {
        // Remove listener from old selection
        removeMouseListener(selection);
        removeMouseMotionListener(selection);

        // Prepare new selection
        if (s != null) {
            s.setWorldRenderer(this);
            s.previousSelection(selection);
        }
        selection = s;

        // Add listeners
        addMouseListener(selection);
        addMouseMotionListener(selection);

        repaint();
    }

    /**
     * Paints connections, nodes, path, values, selections
     *
     * @param gr Graphics context
     */
    @Override
    public void paint(Graphics gr) {
        long time = System.currentTimeMillis();
        Graphics2D g = (Graphics2D) gr;
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());


        // Connections
        if (drawConnections) {
            g.setColor(Color.lightGray);
            world.getAllNodes().forEach(n -> {
                Point3d p1 = n.getPosition();
                n.getConnectionsTo().forEach(c -> {
                    Point3d p2 = ((Node3d) c.getNode()).getPosition();
                    g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                });
            });
        }

        // All nodes
        if (drawNodes) {
            world.getAllNodes().forEach(n -> {
                Point3d p1 = n.getPosition();
                g.setColor(new Color(230, 230, 230));
                g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
                // G, H and F values
            });
        }

        // Path
        if (renderPath) {
            pathRenderer.paint(g);
            //renderPath = false; // TODO
        }
        if (drawValues)
            world.getAllNodes().forEach(n -> {
                Point3d p1 = n.getPosition();
                Graphics2D gg = (Graphics2D) g;
                gg.setFont(new Font("Arial", Font.CENTER_BASELINE, (int) nodeRadius / 2));
                gg.setColor(Color.black);
                String toDraw;
                if(n.getG()==0)
                    toDraw = "F: ?";
                else
                    toDraw = Math.round(n.getF() * 100.0) / 100.0 + "";
                FontMetrics fm = gg.getFontMetrics();
                g.drawString(toDraw, (int) (p1.x - (fm.stringWidth(toDraw) / 2)), (int) p1.y + fm.getHeight() / 2);
                gg.setFont(new Font("Arial", Font.CENTER_BASELINE, (int) nodeRadius / 3));
                fm = gg.getFontMetrics();
                if(n.getG()==0)
                    toDraw="G: ?";
                else
                    toDraw = "G:" + Math.round(n.getG() * 100.0) / 100.0;
                g.drawString(toDraw, (int) (p1.x - (fm.stringWidth(toDraw) / 2)), (int) (p1.y + fm.getHeight() * 1.5));
                if(n.getH()==0)
                    toDraw = "H: ?";
                else
                    toDraw = "H:" + Math.round(n.getH() * 100.0) / 100.0;
                g.drawString(toDraw, (int) (p1.x - (fm.stringWidth(toDraw) / 2)), (int) p1.y - fm.getHeight() / 2);
            });

        // Highlight start an target nodes
        if (drawNodes) {
            g.setFont(new Font("Arial", Font.CENTER_BASELINE, (int) nodeRadius / 2));
            FontMetrics fm = g.getFontMetrics();
            if (world.getStart() != null) {
                g.setColor(new Color(0xA5EF00));
                Point3d p1 = world.getStart().getPosition();
                g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
                g.setColor(Color.BLACK);
                g.drawString("Start", (int) (p1.x - (fm.stringWidth("Start") / 2)), (int) p1.y+fm.getHeight()/4);
            }
            if (world.getTarget() != null) {
                g.setColor(new Color(0xA5EF00));
                Point3d p1 = world.getTarget().getPosition();
                g.fillOval((int) (p1.x - nodeRadius), (int) (p1.y - nodeRadius), (int) (nodeRadius * 2), (int) (nodeRadius * 2));
                g.setColor(Color.BLACK);
                g.drawString("Target", (int) (p1.x - (fm.stringWidth("Target") / 2)), (int) p1.y + fm.getHeight() / 4);
            }
        }

        // Draw selection
        if (selection != null) {
            selection.paint(g);
        }

        System.out.println("WorldRenderer) Frametime: " + (System.currentTimeMillis() - time) + "  Nodes: " + world.getAllNodes().size());
    }

    /**
     *
     * @return Current node radius
     */
    public double getNodeRadius() {
        return nodeRadius;
    }

    /**
     *
     * @param nodeRadius New node radius
     */
    public void setNodeRadius(double nodeRadius) {
        if (nodeRadius < 0)
            nodeRadius = 0;
        this.nodeRadius = nodeRadius;
        pathRenderer.setRadius(nodeRadius);
        repaint();
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

    /**
     *
     * @return Current PathRenderer
     */
    public PathRenderer getPathRenderer() {
        return pathRenderer;
    }
}
