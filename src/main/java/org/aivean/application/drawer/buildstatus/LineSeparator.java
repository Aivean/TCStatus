package org.aivean.application.drawer.buildstatus;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class LineSeparator implements ResizablePaintedRow {

    Color color;
    double h;
    
    public LineSeparator(Color color, double h) {
        this.color = color;
        this.h = h;
    }

    @Override
    public double getYWeight() {
        return h;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.setColor(color);
        graphics.drawLine((int) x, (int) (y + h / 2), (int) (x + w), (int) (y + h / 2));
        return new Rectangle2D.Double(x,y,w,h);
    }
}
