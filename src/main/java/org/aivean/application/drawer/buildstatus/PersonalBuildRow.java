package org.aivean.application.drawer.buildstatus;

import org.aivean.util.FontAlign;
import org.aivean.util.Resource;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class PersonalBuildRow implements ResizablePaintedRow {


    int count;

    public PersonalBuildRow(int count) {
        this.count = count;
    }

    @Override
    public double getYWeight() {
        return 0.75;
    }

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {
        if (Resource.PERSONAL_BUILD_ICON_BLUE != null) {
            graphics.drawImage(Resource.PERSONAL_BUILD_ICON_BLUE, (int) (x+h), (int) y, (int) h, (int) h, null);
        }

        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("Arial", Font.BOLD, (int) h));
        Rectangle2D rectangle2D = FontAlign.drawText(graphics, String.valueOf(count), (int) (x + h * 2.5), (int) y,
                FontAlign.RIGHT, FontAlign.DOWN);

        return new Rectangle2D.Double(x,y,rectangle2D.getWidth(), rectangle2D.getHeight());
    }
}
