package org.aivean.calendar;

import junit.framework.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author izaytsev
 *         3/31/12 3:08 PM
 */
public class TCCalendarEventTest {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    private static final Date date1 = parse("2011-09-10 12:10");
    private static final Date date2 = parse("2011-09-10 12:13");
    private static final Date date2_ = parse("2011-09-10 12:13");
    private static final Date date3 = parse("2011-09-10 13:15");

    private static Date parse(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }


    @Test
    public void testTestOrder() throws Exception {
        List<TCCalendarEvent> tcCalendarEvents = Arrays.asList(
                new TCCalendarEvent(date2_, date3, "test3"),
                new TCCalendarEvent(date2, date3, "test3"),
                new TCCalendarEvent(date2_, date3, "test3"),
                new TCCalendarEvent(date1, date2, "test2"),
                new TCCalendarEvent(date1, date2, "test2_"),
                new TCCalendarEvent(date1, date2, "test2_3"),
                new TCCalendarEvent(date3, date3, "test4"),
                new TCCalendarEvent(date1, date2, "test1")
        );

        TreeSet<TCCalendarEvent> set = new TreeSet<TCCalendarEvent>(tcCalendarEvents);
        for (TCCalendarEvent tcCalendarEvent : set) {
            System.out.println(tcCalendarEvent);
        }

        Assert.assertEquals(6, set.size());
        Assert.assertEquals("test1", set.first().getTitle());
        Assert.assertEquals("test4", set.last().getTitle());
    }
}
