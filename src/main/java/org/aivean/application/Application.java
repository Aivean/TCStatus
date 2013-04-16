package org.aivean.application;

import org.aivean.application.drawer.ViewPainter;
import org.aivean.application.drawer.buildstatus.TCBuildStatusViewPainter;
import org.aivean.application.event.CalendarEvent;
import org.aivean.application.event.DrawableEvent;
import org.aivean.calendar.TCCalendarEvent;
import org.aivean.configuration.BuildConfiguration;
import org.aivean.configuration.Configuration;
import org.aivean.teamcity.API;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author izaytsev
 *         3/1/12 10:20 PM
 */
public class Application {

    private final API api;
    private final FullScreenWindow fullScreenWindow;
    private static RenderTimer renderTimer;
    private static TCStatusRefreshTimer tcStatusRefreshTimer;
    private static TCCalendarManager tcCalendarManager;
    private static Timer eventDispatcherTimer;

    private BuildViewModel[] builds;
    private ViewPainter defaultViewPainter = new TCBuildStatusViewPainter();

    private Queue<DrawableEvent> eventsQueue = new ConcurrentLinkedQueue<DrawableEvent>();

    private static class Holder {
        private final static Application aplicationStatus = new Application();
    }

    Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    protected Application() {
        try {
            configuration = Configuration.load();
            api = new API(configuration.getUser(), configuration.getPass(), configuration.getBaseUrl());
            LinkedList<BuildViewModel> builds = new LinkedList<BuildViewModel>();
            for (BuildConfiguration buildConfiguration : configuration.getBuildConfigurations()) {
                builds.add(new BuildViewModel(buildConfiguration));
            }
            this.builds = builds.toArray(new BuildViewModel[builds.size()]);

            fullScreenWindow = new FullScreenWindow(Boolean.TRUE.equals(configuration.getFullScreen()));

        } catch (IOException e) {
            throw new IllegalStateException("Can't load configuration!", e);
        }

    }

    public void startApplication() {
        fullScreenWindow.setVisible(true);
        renderTimer = new RenderTimer();
        tcStatusRefreshTimer = new TCStatusRefreshTimer(api, configuration.getRefreshIntervalSec()
                * 1000);

         eventDispatcherTimer = new Timer();
         eventDispatcherTimer.scheduleAtFixedRate(new EventProcessor(), 500, 1000);
        if (configuration.getCalendarConfig() != null) {
            tcCalendarManager = new TCCalendarManager(configuration.getCalendarConfig(), 10000);
            eventDispatcherTimer.scheduleAtFixedRate(new CalendarEventProcessorTask(), 10, 10000);
        }
     }

    public static Application getInstance() {
        return Holder.aplicationStatus;
    }

    public BuildViewModel[] getBuilds() {
        return builds;
    }


    public FullScreenWindow getFullScreenWindow() {
        return fullScreenWindow;
    }


    public ViewPainter getCurrentDrawer() {
        DrawableEvent currentEvent = eventsQueue.peek();
        if (currentEvent != null && currentEvent.isProcessing()) {
            return currentEvent.getViewPainter();
        }
        return defaultViewPainter;
    }


    private class EventProcessor extends TimerTask{
        @Override
        public void run() {
            while (eventsQueue.peek()!=null && eventsQueue.peek().isExpired()) {
                eventsQueue.poll();
            }
            if (eventsQueue.peek()!=null && !eventsQueue.peek().isProcessing()) {
                eventsQueue.peek().startProcessing();
            }
        }
    }

    private class CalendarEventProcessorTask extends TimerTask{
        @Override
        public void run() {
            TCCalendarEvent event = tcCalendarManager.processCurrent();
            if (event!=null) {
                addEvent(new CalendarEvent(event));
            }
        }
    }

    public void addEvent(DrawableEvent event) {
        eventsQueue.add(event);
    }

    public TCCalendarEvent getNextCalendarEvent() {
        if (tcCalendarManager != null) {
            TCCalendarEvent next = tcCalendarManager.next();
            if (next!=null && next.getStartTime().getTime() - System.currentTimeMillis() <= 30 * 60 * 1000) {
                return next;
            }
        }
        return null;
    }
}
