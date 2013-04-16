package org.aivean.application.event;

import org.aivean.application.drawer.ViewPainter;

/**
 * @author izaytsev
 *         3/6/12 10:49 PM
 */
public abstract class DrawableEvent {

    long processingStartTime = 0;

    public abstract long getDurationMillis();


    public void startProcessing() {
        processingStartTime = System.currentTimeMillis();
    }

    public boolean isExpired() {
        return isProcessing() && System.currentTimeMillis() - processingStartTime > getDurationMillis();
    }

    public boolean isProcessing() {
        return processingStartTime != 0;
    }

    public abstract ViewPainter getViewPainter();
}
