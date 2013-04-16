package org.aivean.application.drawer.buildstatus;

import org.aivean.util.FontAlign;
import org.aivean.util.StringUtil;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class RunningTimeElement implements PaintedContainer {


    String currentTime;
    String estimatedTime;
    boolean overEstimate;

    public RunningTimeElement(String currentTime, String estimatedTime, boolean overEstimate) {
        this.currentTime = currentTime;
        this.estimatedTime = estimatedTime;
        this.overEstimate = overEstimate;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.setColor(Color.GRAY);
        Font font1 = new Font("Arial", Font.PLAIN, (int) h);
        graphics.setFont(font1);
        FontMetrics metrics = graphics.getFontMetrics(font1);

        int strTimeW = metrics.stringWidth(currentTime);
        int strEstimateW = metrics.stringWidth(estimatedTime);
        String separatorStr = "/";
        int slashW = metrics.stringWidth(separatorStr);
        int strStartX  =  (int) (x + w - (strTimeW + strEstimateW + slashW));
        int strY = (int) (y + h);

        if (!overEstimate) {
            graphics.setColor(Color.GRAY);
        } else {
            graphics.setColor(Color.RED);
        }
        graphics.drawString(currentTime, strStartX, strY);

        graphics.setColor(Color.LIGHT_GRAY);
        graphics.drawString(separatorStr, strStartX + strTimeW, strY);

        graphics.setColor(Color.GRAY);
        graphics.drawString(estimatedTime, strStartX + strTimeW+slashW, strY);
        return new Rectangle2D.Double(x,y,w,h);
    }
}
