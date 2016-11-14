package io.github.hannes99.gui;

import io.github.hannes99.gui.tool_options.*;
import io.github.hannes99.world.AStarWorld;
import io.github.hannes99.world.WorldRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Program to show the A* Algorithm
 */
public class AStarTest extends JFrame implements ComponentListener {
    private AStarWorld aStarWorld;
    private WorldRenderer worldRenderer;
    private ToolPanel toolPanel;
    private ControlPanel controlPanel;
    private ToolOptions toolOptions;

    public AStarTest() {
        // Frame
        setSize(1365, 768);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("AStarTest");
        setLocationRelativeTo(null);

        // Setup A*
        aStarWorld = new AStarWorld();
        worldRenderer = new WorldRenderer(aStarWorld, 1); // 25
        worldRenderer.setInputMode(WorldRenderer.Input.SelectSingle);
        aStarWorld.setAutoConnectToAll(true);

        // Panels
        toolPanel = new ToolPanel(worldRenderer, aStarWorld);
        controlPanel = new ControlPanel(worldRenderer, aStarWorld);
        toolOptions = new SelectionOptions(worldRenderer);

        // ContentPane
        Container c = getContentPane();
        c.setLayout(null);
        c.add(worldRenderer);
        c.add(toolPanel);
        c.add(controlPanel);
        c.add(toolOptions);

        // Register component listener for resize events
        addComponentListener(this);

        // Show
        setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int w = getWidth() / 5;
        int h = getHeight() / 9;
        toolPanel.setBounds(0, 0, w, h);
        toolOptions.setBounds(0, h, w, getHeight() - h);
        worldRenderer.setBounds(w, 0, getWidth() - w - h, getHeight());
        controlPanel.setBounds(getWidth() - h, 0, h, getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }

    public void setToolOptions(ToolOptions t) {
        t.setBounds(toolOptions.getX(), toolOptions.getY(), toolOptions.getWidth(), toolOptions.getHeight());
        remove(toolOptions);
        toolOptions = t;
        add(toolOptions);
    }

    public static class ControlPanel extends Panel {
        private Button bFindPath;

        public ControlPanel(WorldRenderer worldRenderer, AStarWorld aStarWorld) {
            // Find Path
            bFindPath = new Button("Find Path");
            bFindPath.addActionListener(e -> {
                aStarWorld.findPath();
                worldRenderer.repaint();
            });
            add(bFindPath);
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            int a = getWidth();
            bFindPath.setBounds(0, a * 0, a, a);
        }
    }


    public class ToolPanel extends Panel {
        private Button bAddNode, bSelect, bAddArray, bAddShape;

        public ToolPanel(WorldRenderer worldRenderer, AStarWorld aStarWorld) {
            // Select
            bSelect = new Button("Select");
            bSelect.addActionListener(e -> {
                SelectionOptions selectionOptions = new SelectionOptions(worldRenderer);
                setToolOptions(selectionOptions);
            });
            add(bSelect);

            // Add Node
            bAddNode = new Button("Add Node");
            bAddNode.addActionListener(e -> setToolOptions(new AddOptions(worldRenderer)));
            add(bAddNode);

            // Add Array
            bAddArray = new Button("Add Array");
            bAddArray.addActionListener(e -> setToolOptions(new AddArrayOptions(worldRenderer)));
            add(bAddArray);

            // Add Shape
            bAddShape = new Button("Add Shape");
            bAddShape.addActionListener(e -> setToolOptions(new AddShapeOptions(worldRenderer)));
            add(bAddShape);
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            int a = width / 4;
            bSelect.setBounds(a * 0, 0, a, a);
            bAddNode.setBounds(a * 1, 0, a, a);
            bAddArray.setBounds(a * 2, 0, a, a);
            bAddShape.setBounds(a * 3, 0, a, a);
        }
    }

}

