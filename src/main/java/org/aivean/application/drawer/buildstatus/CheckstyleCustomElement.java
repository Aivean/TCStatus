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
public class CheckstyleCustomElement implements PaintedContainer {

    //Tests failed: 1 (1 new), passed: 1130, ignored: 45
    protected static final Pattern pattern = Pattern.compile("([^;]+;\\s*)?(inspections\\s*total:\\s*(\\d+)\\s*(\\(([+\\d]+)?\\s*([-\\d]+)?\\))?)" +
            "?[\\s,]*(errors:\\s*(\\d+)\\s*(\\(" + "([+\\d]+)?\\s*([-\\d]+)?\\))?)?", Pattern.CASE_INSENSITIVE);
    //Number of inspection errors is too large: 13541; inspections total: 13541 (+1), errors: 13541 (+1)


    final Integer inspections;
    final Integer inspectionsNewPlus;
    final Integer inspectionsNewMinus;

    final Integer errors;
    final Integer errorsNewPlus;
    final Integer errorsNewMinus;

    public boolean isWide() {
        StringBuilder sb = new StringBuilder();
        sb.append(inspections==null?"":inspections);
        sb.append(inspectionsNewPlus==null?"":inspectionsNewPlus);
        sb.append(inspectionsNewMinus==null?"":inspectionsNewMinus);
        sb.append(errors==null?"":errors);
        sb.append(errorsNewPlus==null?"":errorsNewPlus);
        sb.append(errorsNewMinus==null?"":errorsNewMinus);
        return sb.toString().length() > 8;
    }

    public CheckstyleCustomElement(Integer errors, Integer errorsNewMinus, Integer errorsNewPlus,
                                   Integer inspections, Integer inspectionsNewMinus, Integer inspectionsNewPlus) {
        this.errors = errors;
        this.errorsNewMinus = errorsNewMinus;
        this.errorsNewPlus = errorsNewPlus;
        this.inspections = inspections;
        this.inspectionsNewMinus = inspectionsNewMinus;
        this.inspectionsNewPlus = inspectionsNewPlus;
    }

    private static Integer intOrNull(String s) {
        if (s==null) return  null;
        try {
            s = s.replaceAll("\\+","");
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    static CheckstyleCustomElement fromString(String s) {
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {

            Integer inspections = intOrNull(matcher.group(3));
            Integer inspectionsNewPlus = intOrNull(matcher.group(5));
            Integer inspectionsNewMinus = intOrNull(matcher.group(6));

            Integer errors = intOrNull(matcher.group(8));
            Integer errorsNewPlus = intOrNull(matcher.group(10));
            Integer errorsNewMinus = intOrNull(matcher.group(11));

            if (inspections == null) {
                return null;
            }
            return new CheckstyleCustomElement(errors, errorsNewMinus, errorsNewPlus, inspections,
                    inspectionsNewMinus, inspectionsNewPlus);
        }
        return null;
    }
    

    @Override
    public Rectangle2D paint(Graphics2D graphics, double x, double y, double w, double h) {

        double curX = x;
        double separator = h/4.0;
        graphics.setFont(new Font("Arial", Font.PLAIN, (int) h));
        if (inspections!=null) {
            curX += separator;
            graphics.setColor(Color.ORANGE);
            curX += drawStr(graphics, String.valueOf(inspections), curX, y);
            if (inspectionsNewPlus!=null || inspectionsNewMinus!=null) {
                graphics.setColor(Color.DARK_GRAY);
                curX += drawStr(graphics, " (", curX, y);
                if (inspectionsNewPlus != null) {
                    graphics.setColor(Color.RED);
                    curX += drawStr(graphics, String.format("+%d", inspectionsNewPlus), curX, y);
                    if (inspectionsNewMinus != null) {
                        curX += drawStr(graphics, " ", curX, y);
                    }
                }

                if (inspectionsNewMinus!=null) {
                    graphics.setColor(Color.GREEN);
                    curX += drawStr(graphics, String.format("%+d", inspectionsNewMinus), curX, y);
                }

                graphics.setColor(Color.DARK_GRAY);
                curX += drawStr(graphics, ")", curX, y);
            }


            if (errors!=null) {
                curX += separator*2;
                graphics.setColor(Color.RED);
                curX += drawStr(graphics, String.format("%d", errors), curX, y);
            }
            if (errorsNewPlus!=null || errorsNewMinus!=null) {


                graphics.setColor(Color.DARK_GRAY);
                curX += drawStr(graphics, " (", curX, y);
                if (errorsNewPlus != null) {
                    graphics.setColor(Color.RED);
                    curX += drawStr(graphics, String.format("+%d", errorsNewPlus), curX, y);
                    if (errorsNewMinus != null) {
                        curX += drawStr(graphics, " ", curX, y);
                    }
                }

                if (errorsNewMinus!=null) {
                    graphics.setColor(Color.GREEN);
                    curX += drawStr(graphics, String.format("%+d", errorsNewMinus), curX, y);
                }

                graphics.setColor(Color.DARK_GRAY);
                curX += drawStr(graphics, ")", curX, y);
            }

        }


        return new Rectangle2D.Double(x, y, x - curX, h);
    }


    private double drawStr(Graphics2D graphics, String str, double x, double y) {
        return FontAlign.drawText(graphics, str, (int) x, (int) y, FontAlign.RIGHT, FontAlign.DOWN).getWidth();
    }

}
