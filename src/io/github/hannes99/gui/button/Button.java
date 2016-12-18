package io.github.hannes99.gui.button;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button
 */
public class Button extends JButton {
    /**
     * Creates a new button with an icon.
     *
     * @param text     Button text
     * @param filename Path to icon
     */
    public Button(String text, String filename) {
        super(text, new MyIcon("src/io/github/hannes99/gui/button/" + filename, true));
        setVerticalAlignment(SwingConstants.BOTTOM);
        setMargin(new Insets(0, 5, 5, 5));
    }

    /**
     * Creates a new button without icon.
     *
     * @param text Button text
     */
    public Button(String text) {
        super(text);
        setMargin(new Insets(0, 0, 0, 0));
    }
}
