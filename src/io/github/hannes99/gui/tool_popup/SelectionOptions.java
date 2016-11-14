package io.github.hannes99.gui.tool_popup;

import io.github.hannes99.gui.Button;
import io.github.hannes99.world.WorldRenderer;

import java.awt.*;

/**
 * Created by robert on 11/13/16.
 */
public class SelectionOptions extends ToolOptions {

    private Button bSelectSingle, bSelectRectangle, bSelectCircle;

    public SelectionOptions(WorldRenderer worldRenderer) {
        // Select single
        PopupMenu m = new PopupMenu();
        bSelectSingle = new Button("Single");
        bSelectSingle.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.SelectSingle);
        });
        add(bSelectSingle);

        // Select square
        bSelectRectangle = new Button("Rectangle");
        bSelectRectangle.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.SelectRectangle);
        });
        add(bSelectRectangle);

        // Select circle
        bSelectCircle = new Button("Circle");
        bSelectCircle.addActionListener(e -> {
            worldRenderer.setInputMode(WorldRenderer.Input.SelectCircle);
        });
        add(bSelectCircle);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        int a = height / 3;
        bSelectSingle.setBounds(0, 0 * a, width, a);
        bSelectRectangle.setBounds(0, 1 * a, width, a);
        bSelectCircle.setBounds(0, 2 * a, width, a);
    }
}
