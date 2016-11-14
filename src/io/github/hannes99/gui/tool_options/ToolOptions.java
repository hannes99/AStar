package io.github.hannes99.gui.tool_options;

import java.awt.*;

/**
 * Created by robert on 11/13/16.
 */
public abstract class ToolOptions extends Panel {
    protected int a;

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        a = height / 18;
    }
}
