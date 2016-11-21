package io.github.hannes99.gui.tool_options;

import io.github.hannes99.world.WorldRenderer;

import javax.swing.event.ChangeEvent;

/**
 * Created by robert on 11/14/16.
 */
public class AddOptions extends ToolOptions {

    SliderPanel sMinDistance, sAutoConnect;

    public AddOptions(WorldRenderer worldRenderer) {
        super(worldRenderer, WorldRenderer.Input.AddNode);

        sMinDistance = new SliderPanel("Min distance", 1, 256, 128) {
            @Override
            public void stateChanged(ChangeEvent e, int value) {
                worldRenderer.setMinDistance(value);
            }
        };
        add(sMinDistance);

        sAutoConnect = new SliderPanel("Auto connect distance: ", 1, 256, 128) {
            @Override
            public void stateChanged(ChangeEvent e, int value) {
                worldRenderer.setAutoConnectDistance(value + 1);
            }
        };
        add(sAutoConnect);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        sMinDistance.setBounds(0, 0 * a, width, 2 * a);
        sAutoConnect.setBounds(0, 2 * a, width, 2 * a);
    }
}
