package io.github.hannes99.gui;

import io.github.hannes99.a_star.AStarWorld;
import io.github.hannes99.gui.debug_renderer.WorldRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Program to show the A* Algorithm
 */
public class AStarTest extends JFrame {
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

    public AStarTest() {
        // Frame
        setSize(1024, 768);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("AStarTest");

        // Setup A*
        aStarWorld = new AStarWorld();
        worldRenderer = new WorldRenderer(aStarWorld, 1); // 25
        worldRenderer.setInputMode(WorldRenderer.Input.AddNode);
        aStarWorld.setAutoConnectToAll(true);

        // Buttons
        bSelect = new Button("Select");
        bSelect.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.Select);
        });
        bFindPath = new Button("Find Path");
        bFindPath.addActionListener(e -> {
            aStarWorld.findPath();
            repaint();
        });

        // Panels
        // TODO move buttons to edit and control container

        // ContentPane
        Container c = getContentPane();
        c.setLayout(null);
        c.add(worldRenderer);
        c.add(bSelect);
        c.add(bFindPath);

        worldRenderer.setBounds(50, 0, getWidth(), getHeight());
        bFindPath.setBounds(0, 0, 50, 50);

        // Show
        setVisible(true);
    }
}
