package io.github.hannes99.gui;

import io.github.hannes99.gui.tool_options.*;
import io.github.hannes99.world.AStarWorld;
import io.github.hannes99.world.WorldRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Program to show the A* Algorithm
 */
public class AStarTest extends JFrame {
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
        c.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int h = c.getHeight() / 9;
                int w = h * 4;
                toolPanel.setBounds(0, 0, w, h);
                toolOptions.setBounds(0, h, w, c.getHeight() - h);
                worldRenderer.setBounds(w, 0, c.getWidth() - w - h, c.getHeight());
                controlPanel.setBounds(c.getWidth() - h, 0, h, c.getHeight());
            }
        });

        c.add(worldRenderer);
        c.add(toolPanel);
        c.add(controlPanel);
        c.add(toolOptions);

        // Show
        setVisible(true);
    }

    public void setToolOptions(ToolOptions t) {
        t.setBounds(toolOptions.getX(), toolOptions.getY(), toolOptions.getWidth(), toolOptions.getHeight());
        remove(toolOptions);
        toolOptions = t;
        add(toolOptions);
    }

    public static class ControlPanel extends Panel {
        private Button bFindPath, bStep, bBack, bClear, bNodeRadius;

        public ControlPanel(WorldRenderer worldRenderer, AStarWorld aStarWorld) {
            // Find Path
            bFindPath = new Button("Find Path");
            bFindPath.addActionListener(e -> {
                aStarWorld.findPath();
                worldRenderer.repaint();
            });
            add(bFindPath);

            // Step
            bStep = new Button("Step");
            bStep.addActionListener(e -> {
                // TODO
            });
            add(bStep);

            // Back
            bBack = new Button("Back");
            bBack.addActionListener(e -> {
                // TODO
            });
            add(bBack);

            // Clear
            bClear = new Button("Clear");
            bClear.addActionListener(e -> { // TODO warning
                aStarWorld.getAllNodes().clear();
                aStarWorld.setStart(null);
                aStarWorld.setTarget(null);
            });
            add(bClear);

            // Node Radius
            bNodeRadius = new Button("Radius");
            bNodeRadius.addActionListener(e -> {
                // TODO
            });
            add(bNodeRadius);
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            int a = getWidth();
            bFindPath.setBounds(0, a * 0, a, a);
            bStep.setBounds(0, a * 1, a, a);
            bBack.setBounds(0, a * 2, a, a);
            bClear.setBounds(0, a * 3, a, a);
            bNodeRadius.setBounds(0, height - a, a, a);
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
            bAddNode = new Button("Add");
            bAddNode.addActionListener(e -> setToolOptions(new AddOptions(worldRenderer)));
            add(bAddNode);

            // Add Array
            bAddArray = new Button("Array");
            bAddArray.addActionListener(e -> setToolOptions(new AddArrayOptions(worldRenderer)));
            add(bAddArray);

            // Add Shape
            bAddShape = new Button("Shape");
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

