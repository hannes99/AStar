package io.github.hannes99.gui.tool_options;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by robert on 11/16/16.
 */
public abstract class SliderPanel extends Panel implements ChangeListener {
    // TODO hon mit dem gemiast in olle panels setlayout(null) tian dass es net beim sliden es layout zerstört
    // Es repaintet jedes mol a di welt, wos man vielleicht net olm sofort tian sollte wegen fps

    private JSlider slider;
    private JLabel lDescription, lValue;

    public SliderPanel(String description, int min, int max, int start) {
        setLayout(null);
        lDescription = new JLabel(description);
        add(lDescription);
        lValue = new JLabel();
        lValue.setHorizontalAlignment(SwingConstants.LEFT);
        add(lValue);
        slider = new JSlider(JSlider.HORIZONTAL, min, max, start);
        slider.addChangeListener(this);
        add(slider);
    }

    abstract void stateChanged(ChangeEvent e, int value);

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        int w = 2 * width / 3;
        int h = height / 2;
        lDescription.setBounds(0, 0, w, h);
        lValue.setBounds(w, 0, width / 3, h);
        slider.setBounds(0, h, width, h);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int v = slider.getValue();
        lValue.setText(String.valueOf(v));
        stateChanged(e, v);
    }
}
