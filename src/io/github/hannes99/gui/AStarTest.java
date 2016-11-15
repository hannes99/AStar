package io.github.hannes99.gui;

import io.github.hannes99.gui.tool_options.*;
import io.github.hannes99.world.AStarWorld;
import io.github.hannes99.world.WorldRenderer;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

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
        setMinimumSize(new Dimension(800, 600));
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("AStarTest");
        setLocationRelativeTo(null);

        // Setup A*
        aStarWorld = new AStarWorld();
        worldRenderer = new WorldRenderer(aStarWorld, 1); // 11
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
        private io.github.hannes99.gui.button.Button bFindPath, bStep, bBack, bClear, bNodeRadius, bCreateRandom;

        public ControlPanel(WorldRenderer worldRenderer, AStarWorld aStarWorld) {
            // Find Path
            bFindPath = new io.github.hannes99.gui.button.Button("Find Path", "findPath.png");
            bFindPath.addActionListener(e -> {
                aStarWorld.findPath();
                worldRenderer.repaint();
            });
            add(bFindPath);

            // Step
            bStep = new io.github.hannes99.gui.button.Button("Step", "step.png");
            bStep.addActionListener(e -> {
                // TODO
            });
            add(bStep);

            // Back
            bBack = new io.github.hannes99.gui.button.Button("Back", "back.png");
            bBack.addActionListener(e -> {
                // TODO
            });
            add(bBack);

            // Clear
            bClear = new io.github.hannes99.gui.button.Button("Clear", "clear.png");
            bClear.addActionListener(e -> { // TODO warning
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

                    if(SwingUtilities.isLeftMouseButton(e))
                        r+=1;

                    if(SwingUtilities.isRightMouseButton(e))
                        r-=1;

                    worldRenderer.setNodeRadius(r);
                    worldRenderer.repaint();
                }
            });
            add(bNodeRadius);

            // Create Random
            bCreateRandom = new io.github.hannes99.gui.button.Button("Random", "random.png");
            bCreateRandom.addActionListener(e -> {
                aStarWorld.createRandom();
                worldRenderer.repaint();
            });
            add(bCreateRandom);
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
            bCreateRandom.setBounds(0, height-(2*a),a,a);
        }
    }


    public class ToolPanel extends Panel {
        private io.github.hannes99.gui.button.Button bAddNode, bSelect, bAddArray, bAddShape;

        public ToolPanel(WorldRenderer worldRenderer, AStarWorld aStarWorld) {
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

}

