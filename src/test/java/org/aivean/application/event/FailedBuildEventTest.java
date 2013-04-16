package org.aivean.application.event;

import org.aivean.application.FullScreenWindow;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author izaytsev
 *         3/11/12 10:40 PM
 */
@Ignore
public class FailedBuildEventTest {

    public static void main(String args[]) throws Exception {

        FullScreenWindow window = new FullScreenWindow(false);
        window.setVisible(true);
        FailedBuildEvent fbe = new FailedBuildEvent("Trunk ",
                /*new LinkedList<String>()*/
                Arrays.asList("izaytsev"/*, "agusyev", "dsklyarov"*/));
        fbe.getViewPainter().draw(window.getGraphics(), window.getWidth() - window.getX(),
                window.getHeight() - window.getY());

        window.update();
        Thread.sleep(100000);
    }
}
