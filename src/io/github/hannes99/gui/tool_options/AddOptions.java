package io.github.hannes99.gui.tool_options;

import io.github.hannes99.world.WorldRenderer;
import io.github.hannes99.world.selection.AddNodeSelection;

import javax.swing.event.ChangeEvent;

/**
 * ToolOptions to create single nodes.
 */
public class AddOptions extends ToolOptions {
    SliderPanel sMinDistance, sAutoConnect;

    /**
     * Creates a new AddOptions instance.
     *
     * @param worldRenderer the WorldRenderer
     */
    public AddOptions(WorldRenderer worldRenderer) {
        super(worldRenderer);
        worldRenderer.setSelection(new AddNodeSelection());

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
