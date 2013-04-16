package org.aivean.application;

import org.aivean.application.event.CalendarEvent;
import org.aivean.calendar.TCCalendarEvent;
import org.aivean.calendar.TCCalendarService;
import org.aivean.configuration.CalendarConfiguration;

import java.util.*;

/**
 * @author izaytsev
 *         2/29/12 11:47 PM
 *
 *  This timer refreshes calendar events
 */
public class TCCalendarManager extends Timer {

    private final int interval;
    private final TCCalendarService calendarService;
    private final TreeSet<TCCalendarEvent> eventsQueue = new TreeSet<TCCalendarEvent>();

    public TCCalendarManager(CalendarConfiguration config, int refreshInterval) {
        this.interval = refreshInterval;
        calendarService = new TCCalendarService(config.getUrl());
        if (config.getUser() != null && config.getPassword()!=null) {
            calendarService.setUserName(config.getUser());
            calendarService.setPassword(config.getPassword());
        }
        schedule(new RefreshTimerTask(), 10);
    }

    class RefreshTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                List<TCCalendarEvent> currentDayEvents = calendarService.getEventsForCurrentPeriod();

                HashSet<TCCalendarEvent> incomingEvents = new HashSet<TCCalendarEvent>(currentDayEvents);
                // delete old events
                HashSet<TCCalendarEvent> queueElementsCopy = new HashSet<TCCalendarEvent>();
                synchronized (eventsQueue) {
                    queueElementsCopy.addAll(eventsQueue);
                    for (TCCalendarEvent event : queueElementsCopy) {
                        if (!incomingEvents.contains(event)) {
                            eventsQueue.remove(event);
                            System.out.println("Removing obsolete event: " + event);
                        }
                    }

                    long time = System.currentTimeMillis() + interval;
                    for (TCCalendarEvent event : currentDayEvents) {
                        if (event.getStartTime().getTime() > time && !eventsQueue.contains(event)) {
                            System.out.println("Adding: " + event);
                            eventsQueue.add(event);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                schedule(new RefreshTimerTask(), interval);
            }
        }
    }


    public TCCalendarEvent next(){
        synchronized (eventsQueue) {
            return (eventsQueue.size() == 0) ? null : eventsQueue.first();
        }
    }

    public TCCalendarEvent processCurrent() {
        synchronized (eventsQueue) {
            if (eventsQueue.size() > 0 && eventsQueue.first().getStartTime().getTime() <= System.currentTimeMillis()) {
                System.out.println("Processing event: "+eventsQueue.first());
                return eventsQueue.pollFirst();
            } else {
                return null;
            }
        }
    }


}
