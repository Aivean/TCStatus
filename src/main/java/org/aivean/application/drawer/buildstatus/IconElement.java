package org.aivean.application.drawer.buildstatus;

import org.aivean.util.FontAlign;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static org.aivean.teamcity.model.BuildStatus.SUCCESS;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public enum IconElement implements PaintedContainer {

    RUNNING_GREEN {
        @Override
        public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
            graphics.setColor(Color.GREEN);
            return drawRunning(graphics, x, y, w, h);
        }
    },
    RUNNING_RED {
        @Override
        public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
            graphics.setColor(Color.RED);
            return drawRunning(graphics, x, y, w, h);
        }
    },
    SUCCESS {
        @Override
        public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
            graphics.setColor(Color.GREEN);
            return drawOval(graphics, x, y, w, h);
        }
    },
    ERROR {
        @Override
        public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
            graphics.setColor(Color.RED);
            return drawOval(graphics, x, y, w, h);
        }
    },
    UNKNOWN {
        @Override
        public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
            graphics.setColor(Color.BLACK);
            Font f = new Font("Arial", Font.PLAIN, (int) h);
            FontAlign.drawText(graphics, "?", (int) (x + w / 2), (int) (y + h / 2), FontAlign.CENTER, FontAlign.CENTER);
            return new Rectangle2D.Double(x,y,w,h);
        }
    };

    private static Rectangle2D drawOval(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.fillOval((int) x, (int) y, (int) w, (int) h);
        return new Rectangle2D.Double(x, y, w, h);
    }


    private static Rectangle2D drawRunning(Graphics2D graphics, double x, double y, double w, double h) {
        graphics.fillPolygon(new int[]{(int) x, (int) (x + w), (int) x}, new int[]{(int) y, (int) (y + h / 2),
                (int) (y + h)}, 3);
        return new Rectangle2D.Double(x,y,w,h);
    }

}
