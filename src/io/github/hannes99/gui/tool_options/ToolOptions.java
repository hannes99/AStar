package io.github.hannes99.gui.tool_options;

import io.github.hannes99.world.WorldRenderer;

import java.awt.*;

/**
 * Created by robert on 11/13/16.
 */
public abstract class ToolOptions extends Panel {
    protected int a;

    /**
     * Force every input mode to set the selection mode.
     *
     * @param worldRenderer
     * @param input
     */
    public ToolOptions(WorldRenderer worldRenderer, WorldRenderer.Input input) {
        worldRenderer.setInputMode(input);
        setLayout(null);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        a = height / 18;
    }
}
