package org.aivean.application.drawer.buildstatus;

import org.aivean.util.FontAlign;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class TextElement implements PaintedContainer {

    private String text;
    private FontAlign horizontalAlign;
    private Color color;

    public TextElement(Color color, String text, FontAlign horizontalAlign) {
        this.color = color;
        this.text = text;
        this.horizontalAlign = horizontalAlign;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.setColor(color);
        graphics.setFont(new Font("Arial", Font.PLAIN, (int) h));
        double drawX = x;
        if (horizontalAlign == FontAlign.CENTER) {
            drawX += w/2;
        } else if (horizontalAlign == FontAlign.LEFT) {
            drawX += w;
        }
        Rectangle2D rectangle2D = FontAlign.drawText(graphics, text, (int) drawX, (int) y, horizontalAlign,
                FontAlign.DOWN);
        return new Rectangle2D.Double(x,y,rectangle2D.getWidth(), rectangle2D.getHeight());
    }
}
