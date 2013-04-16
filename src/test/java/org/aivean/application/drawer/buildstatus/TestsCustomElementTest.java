package org.aivean.application.drawer.buildstatus;

import junit.framework.Assert;
import org.aivean.application.FullScreenWindow;
import org.aivean.application.event.FailedBuildEvent;
import org.aivean.util.FontAlign;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * @author izaytsev
 *         3/21/12 1:26 AM
 */
public class TestsCustomElementTest {


    @Test
    public void testPattern() throws Exception {
        Matcher matcher = TestsCustomElement.pattern.matcher("Tests passed: 1166, ignored: 46");
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " " + matcher.group(i));
            }
        }
    }

    public void testPainting() throws Exception {

        FullScreenWindow window = new FullScreenWindow(false);
        window.setVisible(true);

        TestsCustomElement testsCustomElement;
        testsCustomElement = TestsCustomElement.fromString("Tests failed: 2 (2 new), passed: 1164, ignored: 46");
        TextElement buildNameTextEl = new TextElement(Color.BLACK, "Trunk", FontAlign.RIGHT);
        ResizablePaintedRowSet buildSet = new ResizablePaintedRowSet();
        PaintedContainer rightEl = new TextElement(Color.DARK_GRAY, "#20", FontAlign.LEFT);
        buildSet.addRow(new BuildNameRow(IconElement.SUCCESS, buildNameTextEl, testsCustomElement, rightEl));

        buildSet.paint(window.getGraphics(), 10, 50 , window.getWidth() - window.getX()- 20,
                20);
        window.update();
        Thread.sleep(100000);
    }

    @Test
    public void testDifferentStrings() throws Exception {
        TestsCustomElement testsCustomElement;

        testsCustomElement = TestsCustomElement.fromString("Tests failed: 2 (2 new), passed: 1164, ignored: 46");
        Assert.assertEquals((Integer)2, testsCustomElement.failed);
        Assert.assertEquals((Integer)2, testsCustomElement.failedNew);
        Assert.assertEquals((Integer)1164, testsCustomElement.passed);
        Assert.assertEquals((Integer)46, testsCustomElement.ignored);

        testsCustomElement = TestsCustomElement.fromString("Tests passed: 1166, ignored: 46");
        Assert.assertNull(testsCustomElement.failed);
        Assert.assertNull(testsCustomElement.failedNew);
        Assert.assertEquals((Integer)1166, testsCustomElement.passed);
        Assert.assertEquals((Integer)46, testsCustomElement.ignored);

        testsCustomElement = TestsCustomElement.fromString("Tests failed: 2, passed: 1164, ignored: 46");
        Assert.assertEquals((Integer)2, testsCustomElement.failed);
        Assert.assertNull(testsCustomElement.failedNew);
        Assert.assertEquals((Integer)1164, testsCustomElement.passed);
        Assert.assertEquals((Integer)46, testsCustomElement.ignored);

        testsCustomElement = TestsCustomElement.fromString("Tests failed: 2, passed: 1164");
        Assert.assertEquals((Integer)2, testsCustomElement.failed);
        Assert.assertNull(testsCustomElement.failedNew);
        Assert.assertEquals((Integer)1164, testsCustomElement.passed);
        Assert.assertNull(testsCustomElement.ignored);

        testsCustomElement = TestsCustomElement.fromString("Tests passed: 1164");
        Assert.assertNull(testsCustomElement.failed);
        Assert.assertNull(testsCustomElement.failedNew);
        Assert.assertEquals((Integer)1164, testsCustomElement.passed);
        Assert.assertNull(testsCustomElement.ignored);
    }
}
