package org.aivean.application.event;

import org.aivean.application.drawer.ViewPainter;
import org.aivean.util.AudioPlayer;
import org.aivean.util.FontAlign;
import org.aivean.util.Resource;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;

/**
 * @author izaytsev
 *         3/11/12 5:54 PM
 */
public class FailedBuildEvent extends DrawableEvent{

    private String name;
    String [] users;
    FailedBuildViewPainter failedBuildViewPainter;

    public FailedBuildEvent(String buildName, List<String> users) {
        this.name = buildName;
        this.users = users.toArray(new String[users.size()]);
        this.failedBuildViewPainter = new FailedBuildViewPainter();
    }

    @Override
    public long getDurationMillis() {
        return 10 * 1000;
    }

    @Override
    public ViewPainter getViewPainter() {
        return failedBuildViewPainter;
    }

    @Override
    public void startProcessing() {
        super.startProcessing();
        try {
            AudioPlayer.play(Resource.ALERT_SOUND_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class FailedBuildViewPainter implements ViewPainter {
        @Override
        public void draw(Graphics2D graphics, int w, int h) {
            graphics.setColor(Color.white);
            graphics.setBackground(Color.white);
            graphics.fillRect(0, 0, w, h);

//            graphics.setColor(Color.red);
//            graphics.drawRect(10, 10, w - 20, h - 20);
//
//
//            //cross
//            graphics.setColor(Color.BLUE);
//            graphics.drawLine(w/2,0,w/2,h);
//            graphics.drawLine(0,h/2,w,h/2);


            // build name
            graphics.setColor(Color.RED);
            float initialSize = h / 5;

            Font buildNameFont = new Font("Arial", Font.BOLD, (int) initialSize);

            FontMetrics fontMetrics;
            do {
                 buildNameFont = buildNameFont.deriveFont(initialSize);
                 fontMetrics = graphics.getFontMetrics(buildNameFont);
                 initialSize *= 0.9;
            } while (fontMetrics.getStringBounds(name, graphics).getWidth() > w*0.9);

            graphics.setFont(buildNameFont);
            Rectangle2D buildNameRect = FontAlign.drawText(graphics, name, w / 2, h / 2, FontAlign.CENTER,
                    FontAlign.CENTER);

            graphics.setFont(new Font("Arial", Font.PLAIN, h/8));
            graphics.setColor(Color.BLACK);
            FontAlign.drawText(graphics, "Build failed!", w / 2, h / 2 - h/16 - (int) buildNameRect.getHeight() / 2,
                    FontAlign.CENTER, FontAlign.UP);

            int failerMarginTop = h/12;
            int failerH = (int) Math.min(h / 8, (users.length == 0) ? h / 8 : (h / 2 - failerMarginTop - h / 20 -
                    buildNameRect.getHeight() / 2) / users.length);
            int failerX = w / 2;
            int y = (int) (h/2 + failerMarginTop + buildNameRect.getHeight()/2 + failerH/2) ;

            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.PLAIN, failerH));

            for (String user : users) {
                if (Resource.PERSONAL_BUILD_ICON_RED != null) {
                    graphics.drawImage(Resource.PERSONAL_BUILD_ICON_RED, failerX - failerH, y - failerH / 2, failerH,
                            failerH, null);
                }
                y += FontAlign.drawText(graphics, user, failerX + failerH/4, y,
                        FontAlign.RIGHT, FontAlign.CENTER).getHeight();
            }


        }

    }
}
