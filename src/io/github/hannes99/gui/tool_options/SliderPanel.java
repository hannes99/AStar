package io.github.hannes99.gui.tool_options;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by robert on 11/16/16.
 */
public abstract class SliderPanel extends Panel implements ChangeListener {
    private JSlider slider;
    private JLabel lDescription;
    private JTextField tValue; // TODO input

    public SliderPanel(String description, int min, int max, int start) {
        setLayout(null);
        lDescription = new JLabel(description);
        add(lDescription);
        tValue = new JTextField(String.valueOf(start));
        add(tValue);
        slider = new JSlider(JSlider.HORIZONTAL, min, max, start);
        slider.addChangeListener(this);
        add(slider);
    }

    public abstract void stateChanged(ChangeEvent e, int value);

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        int w = 2 * width / 3;
        int h = height / 2;
        lDescription.setBounds(0, 0, w, h);
        tValue.setBounds(w, 0, width / 3, h);
        slider.setBounds(0, h, width, h);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int v = slider.getValue();
        tValue.setText(String.valueOf(v));
        stateChanged(e, v);
    }

    public int getValue() {
        return slider.getValue();
    }
}
