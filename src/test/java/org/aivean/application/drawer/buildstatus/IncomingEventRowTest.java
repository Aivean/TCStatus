package org.aivean.application.drawer.buildstatus;

import org.aivean.application.FullScreenWindow;
import org.aivean.calendar.TCCalendarEvent;
import org.aivean.util.FontAlign;

import java.awt.*;
import java.util.Date;

/**
 * @author izaytsev
 *         4/5/12 11:36 PM
 */
public class IncomingEventRowTest {
    public static void main(String[] args) throws InterruptedException {
        FullScreenWindow window = new FullScreenWindow(false);
        window.setVisible(true);


        IncomingEventRow incomingEventRow = new IncomingEventRow(new TCCalendarEvent(
                new Date(System.currentTimeMillis()+ 4454400),
                new Date(System.currentTimeMillis()+ 64400),
                "Ad Server stand-up meeting"
        ));

        incomingEventRow.paint(window.getGraphics(), 10, 50 , window.getWidth() - window.getX()- 200,
                20);
        window.update();
        Thread.sleep(100000);
    }
}
