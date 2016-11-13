package io.github.hannes99.gui;

import io.github.hannes99.gui.tool_popup.SelectionOptions;
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
    // AStar
    private AStarWorld aStarWorld;
    private WorldRenderer worldRenderer;
    // Buttons
    private Button bSelect;
    private Button bAddNode;
    private Button bAddBox;
    private Button bAddCircle;
    private Button bClear;
    private Button bUndo;
    private Button bStep;
    private Button bFindPath;

    private ToolPanel toolPanel;
    private ControlPanel controlPanel;

    public AStarTest() {
        // Frame
        setSize(1365, 768);
        setResizable(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("AStarTest");
        setLocationRelativeTo(null);

        // Setup A*
        aStarWorld = new AStarWorld();
        worldRenderer = new WorldRenderer(aStarWorld, 15); // 25
        worldRenderer.setInputMode(WorldRenderer.Input.SelectSingle);
        aStarWorld.setAutoConnectToAll(true);

        // Panels
        toolPanel = new ToolPanel(worldRenderer, aStarWorld);
        controlPanel = new ControlPanel(worldRenderer, aStarWorld);

        // ContentPane
        Container c = getContentPane();
        c.setLayout(null);
        c.add(worldRenderer);
        c.add(toolPanel);
        c.add(controlPanel);

        // Register component listener for resize events
        addComponentListener(this);

        // Show
        setVisible(true);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int panelWidth = getWidth() / 16;
        toolPanel.setBounds(0, 0, panelWidth, getHeight());
        worldRenderer.setBounds(panelWidth, 0, getWidth() - 2 * panelWidth, getHeight());
        controlPanel.setBounds(getWidth() - panelWidth, 0, panelWidth, getHeight());
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
        private Button bAddNode, bRemoveRadius, bSelect, bConnect, bConnectAll;

        public ToolPanel(WorldRenderer worldRenderer, AStarWorld aStarWorld) {

            // Add Node
            bAddNode = new io.github.hannes99.gui.Button("Add Node");
            bAddNode.addActionListener(e -> {
                worldRenderer.setInputMode(WorldRenderer.Input.AddNode);
            });
            add(bAddNode);

            // Connect
            bConnect = new io.github.hannes99.gui.Button("Connect");
            bConnect.addActionListener(e -> {
                worldRenderer.setInputMode(WorldRenderer.Input.Connect);
            });
            add(bConnect);

            // Connect all
            bConnectAll = new io.github.hannes99.gui.Button("ConnectAll");
            bConnectAll.addActionListener(e -> {
                worldRenderer.setInputMode(WorldRenderer.Input.ConnectAll);
            });
            add(bConnectAll);

            // Select
            bSelect = new io.github.hannes99.gui.Button("Select");
            bSelect.addActionListener(e -> {
                SelectionOptions selectionOptions = new SelectionOptions(worldRenderer);
                worldRenderer.add(selectionOptions);
                selectionOptions.setBounds(50, 50, 100, 300);
            });
            add(bSelect);

            // Remove Radius
            bRemoveRadius = new io.github.hannes99.gui.Button("Remove Radius");
            bRemoveRadius.addActionListener(e -> {
                worldRenderer.setInputMode(WorldRenderer.Input.RemoveRadius);
            });
            add(bRemoveRadius);
        }


        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            int a = getWidth();

            bAddNode.setBounds(0, a * 0, a, a);
            bRemoveRadius.setBounds(0, a * 1, a, a);
            bSelect.setBounds(0, a * 2, a, a);
            bConnect.setBounds(0, a * 3, a, a);
            bConnectAll.setBounds(0, a * 4, a, a);
        }
    }

}

