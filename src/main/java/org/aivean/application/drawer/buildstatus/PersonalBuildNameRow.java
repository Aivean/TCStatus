package org.aivean.application.drawer.buildstatus;

import org.aivean.teamcity.model.BuildStatus;
import org.aivean.util.Resource;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class PersonalBuildNameRow implements ResizablePaintedRow {

    final BuildStatus buildStatus;
    final PaintedContainer buildName;
    final PaintedContainer inlineStatus;
    final PaintedContainer rightNumber;

    public PersonalBuildNameRow(BuildStatus buildStatus, PaintedContainer buildName, PaintedContainer inlineStatus,
                                PaintedContainer rightNumber) {
        this.buildStatus = buildStatus;
        this.buildName = buildName;
        this.inlineStatus = inlineStatus;
        this.rightNumber = rightNumber;
    }

    @Override
    public double getYWeight() {
        return 0.75;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.setColor(new Color(250,250,250));
        graphics.fillRect((int)x,(int)y,(int)w,(int)h);
        double  wSpace = h/3;
        double curX = x;
        curX += h;

        // curX += chainPaint(graphics, icon, curX, y, h, h, wSpace);


        BufferedImage icon = Resource.PERSONAL_BUILD_ICON_RED;
        if (buildStatus == BuildStatus.SUCCESS) {
            icon = Resource.PERSONAL_BUILD_ICON_BLUE;
        }
        if (icon != null) {
            graphics.drawImage(icon, (int) (curX+h), (int) y, (int) h, (int) h, null);
        }
        curX += 2 * h + wSpace;
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
