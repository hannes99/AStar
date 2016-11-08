package io.github.hannes99.gui;

import io.github.hannes99.a_star.AStarWorld;
import io.github.hannes99.gui.debug_renderer.DebugRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Program to show the A* Algorithm
 */
public class AStarTest extends JFrame {
    // AStar
    private AStarWorld aStarWorld;
    private DebugRenderer debugRenderer;
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
        debugRenderer = new DebugRenderer(aStarWorld);

        // Buttons
        bSelect = new Button("Select");
        bSelect.addActionListener(e->{
            debugRenderer.setInputMode(DebugRenderer.Input.Select);
        });

        // Panels
        // TODO move buttons to edit and control container

        // ContentPane
        Container c = getContentPane();
        c.setLayout(null);
        c.add(debugRenderer);
        c.add(bSelect);
        c.add(panels);

        // Show
        setVisible(true);
    }
}
