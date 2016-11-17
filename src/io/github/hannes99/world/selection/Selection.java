package io.github.hannes99.world.selection;

import io.github.hannes99.world.Node3d;
import io.github.hannes99.world.WorldRenderer;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Manages selections in WorldRenderer
 */
public abstract class Selection implements MouseInputListener {

    protected WorldRenderer worldRenderer;
    protected Color selectionColor = Color.BLUE;

    public Selection(WorldRenderer worldRenderer) {
        this.worldRenderer = worldRenderer;
    }

    /**
     * Puts all selected nodes into the list
     */
    public abstract ArrayList<Node3d> getSelectedNodes();

    /**
     * Removes all nodes within the selection
     *
     * @return Number of removed nodes
     */
    public abstract int removeSelectedNodes();

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
