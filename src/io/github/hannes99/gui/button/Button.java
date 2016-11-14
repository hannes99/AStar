package io.github.hannes99.gui.button;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button to reduce Code
 */
public class Button extends JButton {
    public Button(String text, String filename) {
        super(text, new StretchIcon("src/io/github/hannes99/gui/button/" + filename));
        setVerticalAlignment(SwingConstants.BOTTOM);
        setMargin(new Insets(5, 5, 5, 5));
    }
}
