package io.github.hannes99.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button to reduce Code
 */
public class Button extends JButton {
    public Button(String text) {
        super(text);
        setVerticalAlignment(SwingConstants.BOTTOM);
        setMargin(new Insets(0, 0, 0, 0));
    }
}
