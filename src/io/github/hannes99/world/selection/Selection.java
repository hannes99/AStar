package io.github.hannes99.world.selection;

import io.github.hannes99.world.Node3d;
import io.github.hannes99.world.WorldRenderer;

import javax.swing.event.MouseInputListener;
import javax.vecmath.Point3d;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Manages selections in WorldRenderer
 */
public abstract class Selection implements MouseInputListener {

    protected WorldRenderer worldRenderer;
    protected Color selectionColor = Color.BLUE;

    public void setWorldRenderer(WorldRenderer w) {
        worldRenderer = w;
    }

    public int removeSelectedNodes() {
        ArrayList<Node3d> nodes = getSelectedNodes();
        int ret = nodes.size();
        for (Node3d n : nodes) {
            worldRenderer.getWorld().destroyNode(n);
        }
        worldRenderer.repaint();
        return ret;
    }

    public void highlightNodes(Graphics2D g) {
        g.setColor(new Color(0x0000FF));
        g.setStroke(new BasicStroke(2));
        for (Node3d n : getSelectedNodes()) {
            Point3d p = n.getPosition();
            double r = worldRenderer.getNodeRadius();
            g.drawOval((int) (p.x - r), (int) (p.y - r), (int) (r * 2), (int) (r * 2));
        }
    }

    /**
     * Puts all selected nodes into the list
     */
    public abstract ArrayList<Node3d> getSelectedNodes();

    /**
     * Draws the selection
     *
     * @param g Grapthics to draw on
     */
    public abstract void paint(Graphics g);

    @Override
    public void mouseClicked(MouseEvent e) {

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
}
