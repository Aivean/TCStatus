package org.aivean.application.event;

import org.aivean.application.drawer.ViewPainter;
import org.aivean.calendar.TCCalendarEvent;
import org.aivean.util.AudioPlayer;
import org.aivean.util.FontAlign;
import org.aivean.util.Resource;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.SimpleDateFormat;

/**
 * @author izaytsev
 *         3/11/12 5:54 PM
 */
public class CalendarEvent extends DrawableEvent {

    private final TCCalendarEvent event;
    private final CalendarEventViewPainter calendarEventViewPainter = new CalendarEventViewPainter();

    public CalendarEvent(TCCalendarEvent event) {
        this.event = event;
    }

    @Override
    public long getDurationMillis() {
        return 15 * 1000;
    }

    @Override
    public ViewPainter getViewPainter() {
        return calendarEventViewPainter;
    }

    @Override
    public void startProcessing() {
        super.startProcessing();
        try {
            AudioPlayer.play(Resource.BEEP_SOUND_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class CalendarEventViewPainter implements ViewPainter {
        @Override
        public void draw(Graphics2D graphics, int w, int h) {

            String name = event.getTitle();

            graphics.setColor(Color.white);
            graphics.setBackground(Color.white);
            graphics.fillRect(0, 0, w, h);


            float initialSize = h / 5;
            Rectangle2D buildNameRect = drawMultilineText(graphics, new Font("Arial", Font.PLAIN, (int) initialSize),
                    new Color(28, 134, 198), name, w / 10, h / 3, w - w / 5, h * 2 / 3);


            SimpleDateFormat df = new SimpleDateFormat("hh:mm");
            String timeString = df.format(event.getStartTime());

            graphics.setFont(new Font("Arial", Font.BOLD, h / 5));
            graphics.setColor(Color.BLACK);
            Rectangle2D timeRect = FontAlign.drawText(graphics, timeString, w / 2 /*+ w/8*/, h / 6, FontAlign.CENTER,
                    FontAlign.CENTER);

            int alarmH = (int) timeRect.getHeight();

            if (Resource.ALARM != null) {
                graphics.drawImage(Resource.ALARM, (int) (timeRect.getX() - alarmH * 1.2), (int) timeRect.getY(),
                        alarmH, alarmH, null);
            }
        }
    }


    private Rectangle2D drawMultilineText(Graphics2D g2d, Font font, Color color, String input, int x, int y,
                                          int width, int maxHeight) {
        float initialSize = font.getSize();
        Font resizedFont;
        int actualY;
        do {
            resizedFont = font.deriveFont(initialSize);
            initialSize *= 0.9;
            actualY = drawMultilineText(g2d, resizedFont, color, input, x, y, width, false);
        } while (actualY > y + maxHeight);
        drawMultilineText(g2d, resizedFont, color, input, x, y, width, true);

        return new Rectangle2D.Double(x, y, width, actualY - y);
    }

    private int drawMultilineText(Graphics2D g2d, Font font, Color color, String input, int x, int y, int width,
                                  boolean actualDrawing) {

        AttributedString attributedString = new AttributedString(input);
        attributedString.addAttribute(TextAttribute.FONT, font);
        attributedString.addAttribute(TextAttribute.FOREGROUND, color);

        AttributedCharacterIterator characterIterator = attributedString.getIterator();
        FontRenderContext fontRenderContext = g2d.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(characterIterator, fontRenderContext);
        while (measurer.getPosition() < characterIterator.getEndIndex()) {
            TextLayout textLayout = measurer.nextLayout(width);
            y += textLayout.getAscent();
            int curX = (int) ((width - textLayout.getBounds().getWidth()) / 2);
            if (actualDrawing) {
                textLayout.draw(g2d, x + curX, y);
            }
            y += textLayout.getDescent() + textLayout.getLeading();
        }

        return y;
    }
}
