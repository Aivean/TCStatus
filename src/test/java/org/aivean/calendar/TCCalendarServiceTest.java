package org.aivean.calendar;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author izaytsev
 *         3/31/12 4:16 PM
 */
@Ignore
public class TCCalendarServiceTest {
//    @Test
//    public void testGetCurrentDayEvents() throws Exception {
//        TCCalendarService tcCalendarService = new TCCalendarService("https://www.google" +
//                ".com/calendar/feeds/********%40gmail" + ".com/private-***************/full");
//
//        for (TCCalendarEvent tcCalendarEvent : tcCalendarService.getEventsForCurrentPeriod()) {
//            System.out.println(tcCalendarEvent);
//        }
//    }



    @Test
    public void testNewCalendar() throws Exception {
        TCCalendarService tcCalendarService = new TCCalendarService("http://www.google.com/calendar/feeds/**************/private/full");
        tcCalendarService.setUserName("************.com");
        tcCalendarService.setPassword("***************");

        for (TCCalendarEvent tcCalendarEvent : tcCalendarService.getEventsForCurrentPeriod()) {
            System.out.println(tcCalendarEvent);
        }
    }
}
