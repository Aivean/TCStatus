package org.aivean.util;

import java.awt.*;
import java.awt.font.GraphicAttribute;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/11/12 11:18 PM
 */
public enum FontAlign {

    UP, DOWN, LEFT, RIGHT, CENTER;

    public static Rectangle2D drawText(Graphics2D graphics, String string, int x, int y, FontAlign horizontalAlign,
                                       FontAlign verticalAlign) {
        return drawText(graphics, string, x, y, 0, horizontalAlign, verticalAlign);
    }

    public static Rectangle2D drawText(Graphics2D graphics, String string, int x, int y, int w,
                                       FontAlign horizontalAlign, FontAlign verticalAlign) {

        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D stringBounds;
        boolean truncated = false;
        while (true) {
            stringBounds = fontMetrics.getStringBounds(string, graphics);
            if (w == 0 || string.isEmpty() || stringBounds.getWidth() <= w) {
                break;
            }
            string = string.substring(0, string.length() - 1);
            truncated = true;
        }
        if (truncated && string.length()>2) {
            string = string.substring(0, string.length() - 2) + "\u2026";
        }

        switch (verticalAlign) {
            case DOWN:
                y += stringBounds.getHeight();
                break;
            case CENTER:
                y += stringBounds.getHeight() / 2;
                break;
        }
        switch (horizontalAlign) {
            case LEFT:
                x -= stringBounds.getWidth();
                break;
            case CENTER:
                x -= stringBounds.getWidth() / 2;
                break;
        }
        graphics.drawString(string, x, y - fontMetrics.getDescent());

//        graphics.drawRect(x, y - (int) (stringBounds.getHeight()), (int) (stringBounds.getWidth()),
//                (int) (stringBounds.getHeight()));

        return new Rectangle2D.Double(x, y - (stringBounds.getHeight()), stringBounds.getWidth(), stringBounds.getHeight());
    }

}
