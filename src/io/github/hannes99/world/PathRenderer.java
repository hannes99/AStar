package io.github.hannes99.world;

import io.github.hannes99.a_star.AStar;

import javax.vecmath.Point3d;
import java.awt.*;

/**
 * Created by robert on 11/16/16.
 */
public class PathRenderer {
    private AStarWorld world;
    private WorldRenderer worldRenderer;
    private double radius;
    private long step = 0;

    public PathRenderer(AStarWorld world, WorldRenderer worldRenderer, double radius) {
        this.world = world;
        this.worldRenderer = worldRenderer;
        setRadius(radius);
    }

    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;

        world.getAllNodes().forEach(n -> n.setPredecessor(null));
        AStar.closedList.clear();
        AStar.openlist.clear();
        boolean drawAll = AStar.findPath(world.getStart(), world.getTarget(), step);

        if (worldRenderer.getDrawNodes()) {
            // Openlist
            g.setColor(new Color(255, 100, 100));
            AStar.openlist.forEach(node -> {
                Point3d p1 = ((Node3d) node).getPosition();
                g.fillOval((int) (p1.x - radius), (int) (p1.y - radius), (int) (radius * 2), (int) (radius * 2));
            });

            // Closed list
            g.setColor(new Color(50, 100, 100));
            AStar.closedList.forEach(node -> {
                Point3d p1 = ((Node3d) node).getPosition();
                g.fillOval((int) (p1.x - radius), (int) (p1.y - radius), (int) (radius * 2), (int) (radius * 2));
            });
        }

        // Draw complete path
        if (drawAll) {
            world.updatePathList();
            if (world.getLastPath() != null) {
                // Connections
                if (worldRenderer.getDrawConnections()) {
                    g.setColor(Color.BLACK);
                    Stroke oldStroke = g.getStroke();
                    g.setStroke(new BasicStroke(2));
                    world.getLastPath().forEach(node -> {
                        Node3d precedessor = (Node3d) node.getPredecessor();
                        if (precedessor != null) { // start node has no precedessor
                            Point3d p1 = ((Node3d) node).getPosition();
                            Point3d p2 = precedessor.getPosition();
                            g.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                        }
                    });
                    g.setStroke(oldStroke);
                }
                // Nodes
                if (worldRenderer.getDrawNodes()) {
                    g.setColor(new Color(50, 255, 50));
                    world.getLastPath().forEach(node -> {
                        Point3d p1 = ((Node3d) node).getPosition();
                        g.fillOval((int) (p1.x - radius), (int) (p1.y - radius), (int) (radius * 2), (int) (radius * 2));
                    });
                }
            }
        }
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public long getStep() {
        return step;
    }

    public void setStep(long step) {
        worldRenderer.setRenderPath(true);
        if (step < 0)
            step = 0;
        this.step = step;
        worldRenderer.repaint();
    }

    public void addToStep(long l) {
        setStep(step + l);
    }
}
