package io.github.hannes99.gui.tool_options;

import io.github.hannes99.gui.button.Button;
import io.github.hannes99.world.AStarWorld;
import io.github.hannes99.world.Node3d;
import io.github.hannes99.world.WorldRenderer;
import io.github.hannes99.world.selection.AddArraySelection;

import javax.swing.event.ChangeEvent;
import java.util.ArrayList;

/**
 * ToolOptions to create a field of nodes.
 */
public class AddArrayOptions extends ToolOptions {
    private SliderPanel sDistance, sRemoveRandom;
    private double removePercent;
    private Button bGenerate, bRemoveRandom;

    /**
     * Creates a new AddArrayOptions instance.
     *
     * @param worldRenderer the WorldRenderer
     */
    public AddArrayOptions(WorldRenderer worldRenderer) {
        super(worldRenderer);
        worldRenderer.setSelection(new AddArraySelection());

        sDistance = new SliderPanel("Distance", 1, 127, 64) {
            @Override
            public void stateChanged(ChangeEvent e, int value) {
                ((AddArraySelection) worldRenderer.getSelection()).setDistance(value);
            }
        };
        add(sDistance);

        sRemoveRandom = new SliderPanel("Percentage to remove: ", 0, 100, 0) {
            @Override
            public void stateChanged(ChangeEvent e, int value) {
            }
        };
        add(sRemoveRandom);

        bGenerate = new Button("Generate");
        bGenerate.addActionListener(e -> {
            ((AddArraySelection) worldRenderer.getSelection()).generateArray();
            ArrayList<Node3d> nodes = ((AddArraySelection) worldRenderer.getSelection()).getSelectedNodes();
            if (sRemoveRandom.getValue() != 0) {
                int i = nodes.size() * (100 - sRemoveRandom.getValue()) / 100;
                AStarWorld world = worldRenderer.getWorld();
                while (nodes.size() > i)
                    world.destroyNode(nodes.remove((int) (Math.random() * nodes.size())));
            }
        });
        add(bGenerate);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);

        sDistance.setBounds(0, 0 * a, width, a);
        sRemoveRandom.setBounds(0, 1 * a, width, a);
        bGenerate.setBounds(0, 2 * a, width, a);
    }
}
