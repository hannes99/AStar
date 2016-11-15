package io.github.hannes99.gui.tool_options;

import io.github.hannes99.world.WorldRenderer;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by robert on 11/14/16.
 */
public class AddOptions extends ToolOptions {

    JSlider sMinDistance, sAutoConnect; // TODO eigene klasse für slider+label+textfield?
    JLabel lMinDistance, lAutoConnect;
    JTextField tMinDistance, tAutoConnect;

    public AddOptions(WorldRenderer worldRenderer) {
        super(worldRenderer, WorldRenderer.Input.AddNode);

        lMinDistance = new JLabel("Min distance: ");
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
            worldRenderer.setMinDistance(sMinDistance.getValue());
        });
        add(sMinDistance);

        lAutoConnect = new JLabel("Auto connect distance: ");
        add(lAutoConnect);
        tAutoConnect = new JTextField(String.valueOf(128));
        tAutoConnect.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent event) {

            }

            @Override
            public void focusLost(FocusEvent event) { // TODO besser / onders mochen
                try {
                    int i = Integer.parseInt(tAutoConnect.getText());
                    sAutoConnect.setValue(i);
                } catch (NumberFormatException e) {
                    tAutoConnect.setText(String.valueOf(sAutoConnect.getValue()));
                }
            }
        });
        add(tAutoConnect);
        sAutoConnect = new JSlider(JSlider.HORIZONTAL, 1, 255, 128);
        sAutoConnect.addChangeListener(changeEvent -> {
            tAutoConnect.setText(String.valueOf(sAutoConnect.getValue()));
            worldRenderer.setAutoConnectDistance(sAutoConnect.getValue() + 1); // TODO bessere möglichkeit als +1?
        });
        add(sAutoConnect);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        lMinDistance.setBounds(0, 0 * a, 2 * width / 3, a);
        tMinDistance.setBounds(2 * width / 3, 0 * a, width / 3, a);
        sMinDistance.setBounds(0, 1 * a, width, a);

        lAutoConnect.setBounds(0, 2 * a, 2 * width / 3, a);
        tAutoConnect.setBounds(2 * width / 3, 2 * a, width / 3, a);
        sAutoConnect.setBounds(0, 3 * a, width, a);
    }
}
