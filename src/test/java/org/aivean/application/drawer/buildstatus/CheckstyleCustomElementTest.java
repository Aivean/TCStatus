package org.aivean.application.drawer.buildstatus;

import junit.framework.Assert;
import org.aivean.application.FullScreenWindow;
import org.aivean.util.FontAlign;
import org.junit.Test;

import java.awt.*;
import java.util.regex.Matcher;

/**
 * @author izaytsev
 *         6/19/12 1:19 PM
 */
public class CheckstyleCustomElementTest {

    @Test
    public void testPattern() throws Exception {
        Matcher matcher = CheckstyleCustomElement.pattern.matcher("Inspections total: 13503 (+10 -47), errors: 13503 (+10 -47)");
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " " + matcher.group(i));
            }
        }
    }



    @Test
    public void testDifferentStrings() throws Exception {
        CheckstyleCustomElement csCustomElement;

        csCustomElement = CheckstyleCustomElement.fromString("Number of inspection errors is too large: 13541; inspections total: 13541 (+2), errors: 1234 (+1)");
        Assert.assertEquals((Integer) 1234, csCustomElement.errors);
        Assert.assertEquals((Integer) 1, csCustomElement.errorsNewPlus);
        Assert.assertEquals((Integer) 13541, csCustomElement.inspections);
        Assert.assertEquals((Integer) 2, csCustomElement.inspectionsNewPlus);
        org.junit.Assert.assertEquals(true, csCustomElement.isWide());

        csCustomElement = CheckstyleCustomElement.fromString("Inspections total: 135, errors: 1234");
        Assert.assertEquals((Integer) 1234, csCustomElement.errors);
        Assert.assertNull(csCustomElement.errorsNewPlus);
        Assert.assertEquals((Integer) 135, csCustomElement.inspections);
        Assert.assertNull(csCustomElement.inspectionsNewPlus);
        org.junit.Assert.assertEquals(false, csCustomElement.isWide());

        csCustomElement = CheckstyleCustomElement.fromString("Inspections total: 13503 (+10 -47), errors: 1234 (+3 -4)");
        Assert.assertEquals((Integer) 1234, csCustomElement.errors);
        Assert.assertEquals((Integer) 3, csCustomElement.errorsNewPlus);
        Assert.assertEquals((Integer) (-4), csCustomElement.errorsNewMinus);
        Assert.assertEquals((Integer) 13503, csCustomElement.inspections);
        Assert.assertEquals((Integer) 10, csCustomElement.inspectionsNewPlus);
        Assert.assertEquals((Integer) (-47), csCustomElement.inspectionsNewMinus);
        org.junit.Assert.assertEquals(true, csCustomElement.isWide());

    }



    //@Test
    public void testPainting() throws Exception {

        FullScreenWindow window = new FullScreenWindow(false);
        window.setVisible(true);

        CheckstyleCustomElement testsCustomElement;
        testsCustomElement = CheckstyleCustomElement.fromString("Number of inspection errors is too large: 13541; inspections total: 13541 (+2), errors: 1234 (+1)");
        TextElement buildNameTextEl = new TextElement(Color.BLACK, "Trunk", FontAlign.RIGHT);
        ResizablePaintedRowSet buildSet = new ResizablePaintedRowSet();
        PaintedContainer rightEl = new TextElement(Color.DARK_GRAY, "#20", FontAlign.LEFT);
        buildSet.addRow(new BuildNameRow(IconElement.SUCCESS, buildNameTextEl, testsCustomElement, rightEl));

        buildSet.paint(window.getGraphics(), 10, 50 , window.getWidth() - window.getX()- 20,
                20);
        window.update();
        Thread.sleep(100000);
    }
}
