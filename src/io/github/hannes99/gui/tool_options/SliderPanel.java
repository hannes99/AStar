package io.github.hannes99.gui.tool_options;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * A component to input numbers.
 */
public abstract class SliderPanel extends Panel implements ChangeListener {
    private JSlider slider;
    private JLabel lDescription;
    private JTextField tValue; // TODO input

    /**
     * Creates a new SliderPanel.
     *
     * @param description The text to show
     * @param min         Min value
     * @param max         Max value
     * @param start       Default value
     */
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

    /**
     * Called when the value changes.
     *
     * @param e The ChangeEvent
     * @param value The new value.
     */
    public abstract void stateChanged(ChangeEvent e, int value);

    /**
     * @return Current value.
     */
    public int getValue() {
        return slider.getValue();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int v = slider.getValue();
        tValue.setText(String.valueOf(v));
        stateChanged(e, v);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        int w = 2 * width / 3;
        int h = height / 2;
        lDescription.setBounds(0, 0, w, h);
        tValue.setBounds(w, 0, width / 3, h);
        slider.setBounds(0, h, width, h);
    }
}
