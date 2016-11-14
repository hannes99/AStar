package io.github.hannes99.gui.tool_options;

import io.github.hannes99.gui.Button;
import io.github.hannes99.world.WorldRenderer;

import javax.swing.*;

/**
 * Created by robert on 11/13/16.
 */
public class SelectionOptions extends ToolOptions {

    private JRadioButton bSelectSingle, bSelectRectangle, bSelectCircle;
    private Button bRemove;

    public SelectionOptions(WorldRenderer worldRenderer) {
        // Only one can be selected
        ButtonGroup buttonGroup = new ButtonGroup();

        // Select single
        bSelectSingle = new JRadioButton("Single");
        bSelectSingle.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.SelectSingle);
        });
        buttonGroup.add(bSelectSingle);
        add(bSelectSingle);

        // Select square
        bSelectRectangle = new JRadioButton("Rectangle");
        bSelectRectangle.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.SelectRectangle);
        });
        buttonGroup.add(bSelectRectangle);
        add(bSelectRectangle);

        // Select circle
        bSelectCircle = new JRadioButton("Circle");
        bSelectCircle.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.SelectCircle);
        });
        buttonGroup.add(bSelectCircle);
        add(bSelectCircle);


        // Remove
        bRemove = new io.github.hannes99.gui.Button("Remove");
        bRemove.addActionListener(e -> {
            if (worldRenderer.getSelection() != null) {
                worldRenderer.getSelection().removeSelectedNodes();
                worldRenderer.repaint();
            }
        });
        add(bRemove);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        bSelectSingle.setBounds(0, 0 * a, width, a);
        bSelectRectangle.setBounds(0, 1 * a, width, a);
        bSelectCircle.setBounds(0, 2 * a, width, a);
        bRemove.setBounds(0, 3 * a, width, a);
    }
}
