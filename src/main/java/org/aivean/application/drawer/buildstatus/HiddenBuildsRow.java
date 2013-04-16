package org.aivean.application.drawer.buildstatus;

import org.aivean.util.FontAlign;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class HiddenBuildsRow implements ResizablePaintedRow {

    String text;

    public HiddenBuildsRow(String text) {
        this.text = text;
    }

    @Override
    public double getYWeight() {
        return 1;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.setColor(Color.GREEN);
        graphics.setFont(new Font("Arial", Font.PLAIN, (int) h));
        Rectangle2D rectangle2D = FontAlign.drawText(graphics, text, (int) (x + w/2), (int) y, FontAlign.CENTER,
                FontAlign.DOWN);

        return new Rectangle2D.Double(x,y,rectangle2D.getWidth(), rectangle2D.getHeight());
    }
}
