package org.aivean.application.drawer.buildstatus;

import org.aivean.util.FontAlign;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class BuildNameRow implements ResizablePaintedRow {

    PaintedContainer icon;
    PaintedContainer buildName;
    PaintedContainer inlineStatus;
    PaintedContainer rightNumber;

    public BuildNameRow(PaintedContainer icon, PaintedContainer buildName, PaintedContainer inlineStatus,
                        PaintedContainer rightNumber) {
        this.icon = icon;
        this.buildName = buildName;
        this.inlineStatus = inlineStatus;
        this.rightNumber = rightNumber;
    }

    @Override
    public double getYWeight() {
        return 1;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.setColor(new Color(250,250,250));
        graphics.fillRect((int)x,(int)y,(int)w,(int)h);
        double  wSpace = h/3;
        double curX = x;
        curX += chainPaint(graphics, icon, curX, y, h, h, wSpace);
        curX += chainPaint(graphics, buildName, curX, y, w, h, wSpace);
        curX += chainPaint(graphics, inlineStatus, curX, y, w, h, wSpace);
        chainPaint(graphics, rightNumber, x, y, w, h, 0);
        return new Rectangle2D.Double(curX,y,w,h);
    }


    private double chainPaint(Graphics2D graphics, PaintedContainer el, double x, double y, double w, double h,
                              double wSpace) {
//        graphics.setColor(Color.BLUE);
//        graphics.drawRect((int)x,(int)y,(int)w,(int)h);

        if (el != null) {
            return el.paint(graphics, x, y, w, h).getWidth() + wSpace;
        }
        return wSpace;
    }

}
