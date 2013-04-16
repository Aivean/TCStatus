package org.aivean.application.drawer.buildstatus;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * @author izaytsev
 *         3/19/12 10:34 PM
 */
public class ResizablePaintedRowSet implements ResizablePaintedRow {

    ArrayList<ResizablePaintedRow> childRows = new ArrayList<ResizablePaintedRow>(3);

    public void addRow(ResizablePaintedRow row) {
        childRows.add(row);
    }

    @Override
    public double getYWeight() {
        double sumH = 0;
        for (ResizablePaintedRow childRow : childRows) {
            sumH += childRow.getYWeight();
        }
        return sumH;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        double sumH = getYWeight();

        double drawnH = 0;
        for (ResizablePaintedRow row : childRows) {
            double curH = row.getYWeight() * h / sumH;
            Rectangle2D rectangle2D = row.paint(graphics, x, y + drawnH, w, curH);
//            graphics.setColor(Color.GREEN);
//            graphics.drawRect((int) rectangle2D.getX(), (int) rectangle2D.getY(), (int) rectangle2D.getWidth(),
//                    (int) rectangle2D.getHeight());

            drawnH += curH;
        }
        return new Rectangle2D.Double(x, y, w, h);
    }
}
