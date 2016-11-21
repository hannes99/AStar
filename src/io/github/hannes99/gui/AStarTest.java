package io.github.hannes99.gui;

import io.github.hannes99.gui.tool_options.*;
import io.github.hannes99.world.AStarWorld;
import io.github.hannes99.world.WorldRenderer;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

/**
 * Program to show the A* Algorithm
 */
public class AStarTest extends JFrame {
    private AStarWorld world;
    private WorldRenderer worldRenderer;
    private ToolPanel toolPanel;
    private ControlPanel controlPanel;
    private ToolOptions toolOptions;
    private SettingsPanel settingsPanel;

    public AStarTest() {
        // Frame
        setSize(1365, 768);
        setMinimumSize(new Dimension(800, 600));
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("AStarTest");
        setLocationRelativeTo(null);

        // Setup A*
        world = new AStarWorld();
        worldRenderer = new WorldRenderer(world, 11); // 11
        worldRenderer.setInputMode(WorldRenderer.Input.SelectSingle);

        // Panels
        toolPanel = new ToolPanel(worldRenderer);
        controlPanel = new ControlPanel(worldRenderer);
        toolOptions = new SelectionOptions(worldRenderer);
        settingsPanel = new SettingsPanel(worldRenderer);

        // ContentPane
        Container c = getContentPane();
        c.setLayout(null);
        c.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                int h = c.getHeight() / 9;
                int w = h * 4;
                toolPanel.setBounds(0, 0, w, h);
                toolOptions.setBounds(0, h, w, c.getHeight() - 3 * h / 2);
                settingsPanel.setBounds(0, c.getHeight() - h / 2, w, h / 2);
                worldRenderer.setBounds(w, 0, c.getWidth() - w - h, c.getHeight());
                controlPanel.setBounds(c.getWidth() - h, 0, h, c.getHeight());
            }
        });

        c.add(worldRenderer);
        c.add(toolPanel);
        c.add(controlPanel);
        c.add(toolOptions);
        c.add(settingsPanel);
        // Show
        setVisible(true);
    }

    public void setToolOptions(ToolOptions t) {
        t.setBounds(toolOptions.getX(), toolOptions.getY(), toolOptions.getWidth(), toolOptions.getHeight());
        remove(toolOptions);
        toolOptions = t;
        add(toolOptions);
    }

    private class ControlPanel extends Panel {
        private io.github.hannes99.gui.button.Button bFindPath, bStep, bStepCount, bBack, bClear, bNodeRadius;
        private int stepCount;

        public ControlPanel(WorldRenderer worldRenderer) {
            setLayout(null);

            // Find Path
            bFindPath = new io.github.hannes99.gui.button.Button("Find Path", "findPath.png");
            bFindPath.addActionListener(e -> worldRenderer.getPathRenderer().setStep(0));
            add(bFindPath);

            // Step count
            bStepCount = new io.github.hannes99.gui.button.Button("1", "");
            bStepCount.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        stepCount += 1;
                    }
                    if (SwingUtilities.isRightMouseButton(e)) {
                        stepCount -= 1;
                        if (stepCount < 0)
                            stepCount = 0;
                    }
                    bStepCount.setText(String.valueOf((int) Math.pow(2, stepCount)));
                }
            });
            add(bStepCount);

            // Step
            bStep = new io.github.hannes99.gui.button.Button("Step", "step.png");
            bStep.addActionListener(e -> worldRenderer.getPathRenderer().addToStep((int) Math.pow(2, stepCount)));
            add(bStep);

            // Back
            bBack = new io.github.hannes99.gui.button.Button("Back", "back.png");
            bBack.addActionListener(e -> worldRenderer.getPathRenderer().addToStep((int) -Math.pow(2, stepCount)));
            add(bBack);

            // Clear
            bClear = new io.github.hannes99.gui.button.Button("Clear", "clear.png");
            bClear.addActionListener(e -> {
                int res = JOptionPane.showConfirmDialog(worldRenderer, "Are you sure you want to delete all nodes?", "Delete Nodes", JOptionPane.YES_NO_OPTION);
                if (res == 0)
                    worldRenderer.clear();
            });
            add(bClear);

            // Node Radius
            bNodeRadius = new io.github.hannes99.gui.button.Button("Radius", "radius.png");
            bNodeRadius.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    double r = worldRenderer.getNodeRadius();

                    if (SwingUtilities.isLeftMouseButton(e))
                        r += 1;

                    if (SwingUtilities.isRightMouseButton(e))
                        r -= 1;

                    if(r>10)
                        worldRenderer.setDrawValues(true);
                    else
                        worldRenderer.setDrawValues(false);
                    worldRenderer.setNodeRadius(r);
                }
            });
            add(bNodeRadius);
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            int a = getWidth();
            bFindPath.setBounds(0, a * 0, a, a);
            bStepCount.setBounds(0, a * 1, a, a);
            bStep.setBounds(0, a * 2, a, a);
            bBack.setBounds(0, a * 3, a, a);
            bClear.setBounds(0, a * 4, a, a);
            bNodeRadius.setBounds(0, height - a, a, a);
        }
    }


    public class ToolPanel extends Panel {
        private io.github.hannes99.gui.button.Button bAddNode, bSelect, bAddArray, bAddShape;

        public ToolPanel(WorldRenderer worldRenderer) {
            setLayout(null);

            // Select
            bSelect = new io.github.hannes99.gui.button.Button("Select", "select.png");
            bSelect.addActionListener(e -> setToolOptions(new SelectionOptions(worldRenderer)));
            add(bSelect);

            // Add Node
            bAddNode = new io.github.hannes99.gui.button.Button("Add", "add.png");
            bAddNode.addActionListener(e -> setToolOptions(new AddOptions(worldRenderer)));
            add(bAddNode);

            // Add Array
            bAddArray = new io.github.hannes99.gui.button.Button("Array", "array.png");
            bAddArray.addActionListener(e -> setToolOptions(new AddArrayOptions(worldRenderer)));
            add(bAddArray);

            // Add Shape
            bAddShape = new io.github.hannes99.gui.button.Button("Shape", "shapes.png");
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

    private class SettingsPanel extends Panel { // TODO
        JToggleButton tNodes, tConnections;

        public SettingsPanel(WorldRenderer worldRenderer) {
            setLayout(null);

            tNodes = new JToggleButton("Nodes");
            tNodes.setSelected(worldRenderer.getDrawNodes());
            tNodes.addActionListener(e -> worldRenderer.setDrawNodes(tNodes.isSelected()));
            add(tNodes);

            tConnections = new JToggleButton("Connections");
            tConnections.setSelected(worldRenderer.getDrawConnections());
            tConnections.addActionListener(e -> worldRenderer.setDrawConnections(tConnections.isSelected()));
            add(tConnections);
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);

            tNodes.setBounds(0, 0, width / 2, height);
            tConnections.setBounds(width / 2, 0, width / 2, height);
        }
    }

}

