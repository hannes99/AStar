package io.github.hannes99.gui.button;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

/**
 * Created by robert on 11/15/16.
 */
public class MyIcon extends ImageIcon {

    private boolean proportionate = true;

    public MyIcon(String file, boolean proportionate) {
        super(file);
        this.proportionate = proportionate;
    }

    @Override
    public synchronized void paintIcon(Component c, Graphics g, int x, int y) {
        Image image = getImage();
        if (image != null) {
            Insets insets = ((Container) c).getInsets();
            x = insets.left;
            y = insets.top;

            int w = c.getWidth() - x - insets.right;
            int h = c.getHeight() - y - insets.bottom;

            if (proportionate) {
                int iw = image.getWidth(c);
                int ih = image.getHeight(c);

                if ((iw * h) < (ih * w)) {
                    iw = (h * iw) / ih;
                    x += (w - iw) / 2;
                    w = iw;
                } else {
                    ih = (w * ih) / iw;
                    y += (h - ih) / 2;
                    h = ih;
                }
            }

            h /= 3;
            w /= 3;
            x += w;
            y += h;

            ImageObserver io = getImageObserver();

            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2d = bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
            g2d.drawImage(image, 0, 0, w, h, io == null ? c : io);
            g2d.dispose();

            g.drawImage(bi, x, y, w, h, io == null ? c : io);
        }
    }

    @Override
    public int getIconWidth() {
        return 0;
    }

    @Override
    public int getIconHeight() {
        return 0;
    }
}
