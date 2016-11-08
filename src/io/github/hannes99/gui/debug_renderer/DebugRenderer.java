package io.github.hannes99.gui.debug_renderer;

import io.github.hannes99.AStar.Position;
import io.github.hannes99.a_star.AStarWorld;
import io.github.hannes99.a_star.Node;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Draws the state of the AStar Algorithm.
 */
public class DebugRenderer extends JComponent implements MouseInputListener, MouseWheelListener{

    private AStarWorld aStarWorld;
    private Input mode;

    // private double scale = 1; // without scaling and scrolling for now

    private Node selected;

    public DebugRenderer(AStarWorld aStarWorld) {
        setWorld(aStarWorld);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    public void setWorld(AStarWorld aStarWorld) {
        this.aStarWorld = aStarWorld;
    }

    public void setInputMode(Input im) {
        mode = im;
    }

    public Node getClickedNode(double x, double y) {
        Node ret = aStarWorld.getNearestNode(x, y);
        if (ret.getPosition().getDistTo(new Position(x, y) < radiusFaAnNode))
        return ret;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (mode) {
            case Select: {
                selected = getClickedNode(e.getX(), e.getY());
                break;
            }
        }
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
        Select, AddNode, AddBox, AddCircle,
    }


}
