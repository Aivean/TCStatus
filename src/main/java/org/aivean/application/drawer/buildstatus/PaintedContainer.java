package org.aivean.application.drawer.buildstatus;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/19/12 7:34 PM
 */
public interface PaintedContainer {

    /**
     * Paint component
     * @param graphics
     * @param x left x coord
     * @param y top y coord
     * @param w width
     * @param h height
     * @return actual painted rectangle
     */
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h);
}
