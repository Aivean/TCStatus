package org.aivean.application.event;

import org.aivean.application.FullScreenWindow;
import org.aivean.calendar.TCCalendarEvent;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.Date;

/**
 * @author izaytsev
 *         3/11/12 10:40 PM
 */
@Ignore
public class CalendarEventTest {

    public static void main(String args[]) throws Exception {

        FullScreenWindow window = new FullScreenWindow(false);
        window.setVisible(true);
        CalendarEvent event = new CalendarEvent(new TCCalendarEvent(new Date(), new Date(System.currentTimeMillis() +
                3600 * 1000), "Ad Server Stand-up meeting"));
        event.getViewPainter().draw(window.getGraphics(), window.getWidth() - window.getX(),
                window.getHeight() - window.getY());

        window.update();
        Thread.sleep(100000);
    }
}
