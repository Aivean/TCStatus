package org.aivean.application.drawer.buildstatus;

import org.aivean.util.FontAlign;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class BuildStatusRow implements ResizablePaintedRow {

    String text;

    public BuildStatusRow(String text) {
        this.text = text;
    }

    @Override
    public double getYWeight() {
        return 0.6;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.PLAIN, (int) h));
        Rectangle2D rectangle2D = FontAlign.drawText(graphics, text, (int) (x + h*2), (int) y, FontAlign.RIGHT,
                FontAlign.DOWN);

        return new Rectangle2D.Double(x,y,rectangle2D.getWidth(), rectangle2D.getHeight());
    }
}
