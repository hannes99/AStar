package io.github.hannes99.gui;

import io.github.hannes99.AStar.AStar;
import io.github.hannes99.gui.debug_renderer.DebugRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * Program to show the A* Algorithm
 */
public class AStarTest extends JFrame {
    // AStar
    private AStar aStar;
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
        aStar = new AStar();
        debugRenderer = new DebugRenderer(aStar);

        // Buttons
        bSelect = new Button("Select");
        bSelect.addActionListener(e->{
            debugRenderer.setAction();
        });


        // ContentPane
        Container c = getContentPane();
        c.setLayout(null);
        c.add(debugRenderer);
        c.add();


        // Show
        setVisible(true);
    }
}
