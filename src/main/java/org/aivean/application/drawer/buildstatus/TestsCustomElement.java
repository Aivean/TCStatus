package org.aivean.application.drawer.buildstatus;

import org.aivean.util.FontAlign;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author izaytsev
 *         3/19/12 10:50 PM
 */
public class TestsCustomElement implements PaintedContainer {

    //Tests failed: 1 (1 new), passed: 1130, ignored: 45
    protected static final Pattern pattern = Pattern.compile(
            "Tests\\s*(failed: (\\d+)\\s*(\\((\\d+) new\\))?)?[\\s,]*(passed: (\\d+))?[\\s,]*(ignored: (\\d+))?.*");
    
    Integer passed;
    Integer failed;
    Integer failedNew;
    Integer ignored;


    protected TestsCustomElement(Integer passed, Integer failed, Integer failedNew, Integer ignored) {
        this.passed = passed;
        this.failed = failed;
        this.failedNew = failedNew;
        this.ignored = ignored;
    }

    private static Integer intOrNull(String s) {
        if (s==null) return  null;
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    static TestsCustomElement fromString(String s) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            Integer passed1 = intOrNull(matcher.group(6));
            Integer failed1 = intOrNull(matcher.group(2));
            Integer failedNew1 = intOrNull(matcher.group(4));
            Integer ignored1 = intOrNull(matcher.group(8));
            if (passed1==null && failed1==null && failedNew1==null && ignored1==null) {
                return null;
            }
            return new TestsCustomElement(passed1, failed1, failedNew1, ignored1);
        }
        return null;
    }
    

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {

        double curX = x;
        double separator = h/4.0;
        graphics.setFont(new Font("Arial", Font.PLAIN, (int) h));
        if (failed!=null) {
            curX += separator;
            graphics.setColor(Color.RED);
            curX += drawStr(graphics, String.valueOf(failed), curX, y);
            if (failedNew!=null) {
                graphics.setColor(Color.ORANGE);
                curX += drawStr(graphics, String.format("(%d)", failedNew), curX, y);
            }
        }
        if (passed!=null) {
            curX += separator;
            graphics.setColor(Color.GREEN);
            curX += drawStr(graphics, String.valueOf(passed), curX, y);
        }
        if (ignored!=null) {
            curX += separator;
            graphics.setColor(Color.LIGHT_GRAY);
            curX += drawStr(graphics, String.valueOf(ignored), curX, y);
        }
        return new Rectangle2D.Double(x, y, x - curX, h);
    }


    private double drawStr(Graphics2D graphics, String str, double x, double y) {
        return FontAlign.drawText(graphics, str, (int) x, (int) y, FontAlign.RIGHT, FontAlign.DOWN).getWidth();
    }

}
