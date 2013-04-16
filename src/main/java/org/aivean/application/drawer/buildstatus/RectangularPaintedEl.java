package org.aivean.application.drawer.buildstatus;

import org.aivean.util.FontAlign;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class RectangularPaintedEl implements ResizablePaintedRow {

    String text;

    public RectangularPaintedEl(String text) {
        this.text = text;
    }

    @Override
    public double getYWeight() {
        return 1;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.setColor(Color.RED);
        graphics.drawRect((int) x,(int) y,(int) w,(int) h);

        graphics.setFont(new Font("Arial", Font.PLAIN, (int) h));
        Rectangle2D rectangle2D = FontAlign.drawText(graphics, text, (int) x, (int) y, FontAlign.RIGHT,
                FontAlign.DOWN);


        return new Rectangle2D.Double(x,y,rectangle2D.getWidth(), rectangle2D.getHeight());
    }
}
