package io.github.hannes99.gui.tool_options;

import io.github.hannes99.gui.button.Button;
import io.github.hannes99.world.Node3d;
import io.github.hannes99.world.WorldRenderer;
import io.github.hannes99.world.selection.Selection;
import io.github.hannes99.world.selection.SingleSelection;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by robert on 11/13/16.
 */
public class SelectionOptions extends ToolOptions {

    private JRadioButton bSelectSingle, bSelectRectangle, bSelectCircle;
    private Button bConnect, bSetStart, bSetTarget, bRemove;

    public SelectionOptions(WorldRenderer worldRenderer) {
        super(worldRenderer, WorldRenderer.Input.SelectRectangle);

        // Only one can be selected
        ButtonGroup buttonGroup = new ButtonGroup();

        // Select single
        bSelectSingle = new JRadioButton("Single");
        bSelectSingle.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.SelectSingle);
            bConnect.setEnabled(false);
            bSetStart.setEnabled(true);
            bSetTarget.setEnabled(true);
        });
        buttonGroup.add(bSelectSingle);
        add(bSelectSingle);

        // Select Rectangle
        bSelectRectangle = new JRadioButton("Rectangle");
        bSelectRectangle.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.SelectRectangle);
            bConnect.setEnabled(true);
            bSetStart.setEnabled(false);
            bSetTarget.setEnabled(false);
        });
        buttonGroup.add(bSelectRectangle);
        add(bSelectRectangle);

        // Select circle
        bSelectCircle = new JRadioButton("Circle");
        bSelectCircle.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.SelectCircle);
            bConnect.setEnabled(true);
            bSetStart.setEnabled(false);
            bSetTarget.setEnabled(false);
        });
        buttonGroup.add(bSelectCircle);
        add(bSelectCircle);

        // Connect
        bConnect = new Button("Connect");
        bConnect.addActionListener(e -> { // TODO
            Selection s = worldRenderer.getSelection();
            if (s != null) {
                ArrayList<Node3d> nodes = s.getSelectedNodes();
                for (Node3d n : nodes) {
                    for (Node3d o : nodes) {
                        n.connectTo(o);
                    }
                }
                worldRenderer.repaint();
            }
        });
        add(bConnect);

        // Set Start
        bSetStart = new Button("Set Start");
        bSetStart.addActionListener(e -> {
            Selection s = worldRenderer.getSelection();
            if (s instanceof SingleSelection) {
                Node3d n = null;
                if (s.getSelectedNodes().size() > 0)
                    n = s.getSelectedNodes().get(0);
                worldRenderer.getWorld().setStart(n);
                worldRenderer.repaint();
            }
        });
        add(bSetStart);

        // Set Target
        bSetTarget = new Button("Set Target");
        bSetTarget.addActionListener(e -> {
            Selection s = worldRenderer.getSelection();
            if (s instanceof SingleSelection) {
                Node3d n = null;
                if (s.getSelectedNodes().size() > 0) {
                    n = s.getSelectedNodes().get(0);
                    worldRenderer.getWorld().setTarget(n);
                }
                worldRenderer.repaint();
            }
        });
        add(bSetTarget);
        // Remove
        bRemove = new Button("Remove");
        bRemove.addActionListener(e -> {
            if (worldRenderer.getSelection() != null) {
                worldRenderer.getSelection().removeSelectedNodes();
                worldRenderer.repaint();
            }
        });
        add(bRemove);

        // Select Rectangle
        bSelectRectangle.doClick(); // TODO better way?

        // TODO button to invert selected nodes
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        bSelectSingle.setBounds(0, 0 * a, width, a);
        bSelectRectangle.setBounds(0, 1 * a, width, a);
        bSelectCircle.setBounds(0, 2 * a, width, a);
        bConnect.setBounds(0, 3 * a, width, a);
        bSetStart.setBounds(0, 4 * a, width, a);
        bSetTarget.setBounds(0, 5 * a, width, a);
        bRemove.setBounds(0, 6 * a, width, a);
    }
}
