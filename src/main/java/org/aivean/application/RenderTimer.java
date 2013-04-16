package org.aivean.application;

import org.aivean.application.drawer.ViewPainter;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author izaytsev
 *         2/29/12 11:47 PM
 *
 *  Timer that provides rendering cycle
 */
public class RenderTimer extends Timer {

    public RenderTimer() {
        scheduleAtFixedRate(new RenderTimerTask(), 10, 500);
    }

    class RenderTimerTask extends TimerTask {
        @Override
        public void run() {
            FullScreenWindow window = Application.getInstance().getFullScreenWindow();
            final int WIDTH_HEIGHT_D = 7;
            Graphics2D graphics = window.getGraphics();
            ViewPainter viewPainter = Application.getInstance().getCurrentDrawer();
            viewPainter.draw(graphics, window.getWidth()-window.getX(), window.getHeight()-window.getY());
            window.update();
        }
    }



}
