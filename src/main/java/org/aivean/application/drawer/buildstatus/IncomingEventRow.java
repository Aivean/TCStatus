package org.aivean.application.drawer.buildstatus;

import org.aivean.calendar.TCCalendarEvent;
import org.aivean.util.FontAlign;
import org.aivean.util.Resource;
import org.aivean.util.StringUtil;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.text.SimpleDateFormat;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class IncomingEventRow implements ResizablePaintedRow {


    final TCCalendarEvent event;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm");

    public IncomingEventRow(TCCalendarEvent event) {
        this.event = event;
    }

    @Override
    public double getYWeight() {
        return 1;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {

        graphics.setFont(new Font("Arial", Font.BOLD, (int) h));

        graphics.drawImage(Resource.ALARM, (int) x, (int) y, (int) h, (int) h, null);
        int startX = (int) (x + h * 1.5);

        Rectangle2D timeRect = FontAlign.drawText(graphics, DATE_FORMAT.format(event.getStartTime()), startX,
                (int) y, FontAlign.RIGHT, FontAlign.DOWN);

        startX += timeRect.getWidth();
        startX += h/2;


        long timeSec = (event.getStartTime().getTime() - System.currentTimeMillis()) / 1000;
        String timeLeftStr = StringUtil.formatTimeSec(timeSec);
        Rectangle2D timeLeftRect = FontAlign.drawText(graphics, timeLeftStr, (int) (x+w), (int) y, FontAlign.LEFT,
                FontAlign.DOWN);

        int endX = (int) (timeLeftRect.getX() - h/2);


        graphics.setFont(new Font("Arial", Font.PLAIN, (int) h));
        FontAlign.drawText(graphics, event.getTitle(), startX, (int) y, endX-startX,FontAlign.RIGHT, FontAlign.DOWN);

        return new Rectangle2D.Double(x,y,w,h);
    }
}
