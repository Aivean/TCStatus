package org.aivean.application.drawer.buildstatus;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         6/19/12 6:06 PM
 */
public class PaintedContainerRow implements ResizablePaintedRow {

    private final double height;
    private final PaintedContainer delegator;


    public PaintedContainerRow(PaintedContainer delegator, double height) {
        this.delegator = delegator;
        this.height = height;
    }

    @Override
    public double getYWeight() {
        return height;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        return delegator.paint(graphics, x, y, w, h);
    }
}
