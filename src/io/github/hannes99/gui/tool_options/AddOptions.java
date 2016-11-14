package io.github.hannes99.gui.tool_options;

import io.github.hannes99.world.WorldRenderer;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by robert on 11/14/16.
 */
public class AddOptions extends ToolOptions {

    JSlider sMinDistance;
    JLabel lMinDistance;
    JTextField tMinDistance;

    public AddOptions(WorldRenderer worldRenderer) {
        lMinDistance = new JLabel("Min Distance: ");
        add(lMinDistance);
        tMinDistance = new JTextField(String.valueOf(128));
        tMinDistance.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent event) {

            }

            @Override
            public void focusLost(FocusEvent event) { // TODO besser / onders mochen
                try {
                    int i = Integer.parseInt(tMinDistance.getText());
                    sMinDistance.setValue(i);
                } catch (NumberFormatException e) {
                    tMinDistance.setText(String.valueOf(sMinDistance.getValue()));
                }
            }
        });
        add(tMinDistance);
        sMinDistance = new JSlider(JSlider.HORIZONTAL, 1, 255, 128);
        sMinDistance.addChangeListener(changeEvent -> {
            tMinDistance.setText(String.valueOf(sMinDistance.getValue()));
        });
        add(sMinDistance);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        lMinDistance.setBounds(0, 0 * a, width / 2, a);
        tMinDistance.setBounds(width / 2, 0 * a, width / 2, a);
        sMinDistance.setBounds(0, 1 * a, width, a);
    }
}
