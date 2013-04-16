package org.aivean.calendar;

import com.google.gdata.client.ClientLoginAccountType;
import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author izaytsev
 *         3/31/12 12:23 AM
 */
public class TCCalendarService {

    private final String fullFeedUrl;

    private String userName;
    private String password;

    public TCCalendarService(String fullFeedUrl) {
        this.fullFeedUrl = fullFeedUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TCCalendarEvent> getEventsForCurrentPeriod() throws IOException, ServiceException {

        // Set up the URL and the object that will handle the connection:
        URL feedUrl = new URL(fullFeedUrl);
        CalendarService myService = new CalendarService("TCStatus");
        if (userName!=null && password!=null) {
            myService.setUserCredentials(userName, password);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.HOUR_OF_DAY, -1);

        DateTime start = new DateTime(calendar.getTime());
        calendar.add(Calendar.DATE, 1);
        DateTime end = new DateTime(calendar.getTime());


        CalendarQuery myQuery = new CalendarQuery(feedUrl);
        myQuery.setMinimumStartTime(start);
        myQuery.setMaximumStartTime(end);

        CalendarEventFeed myFeed = myService.getFeed(myQuery, CalendarEventFeed.class);

        LinkedList<TCCalendarEvent> resultEvents = new LinkedList<TCCalendarEvent>();
        for (CalendarEventEntry entry : myFeed.getEntries()) {
            for (When when : entry.getTimes()) {
                resultEvents.add(new TCCalendarEvent(
                        new Date(when.getStartTime().getValue()),
                        new Date(when.getEndTime().getValue()),
                        entry.getTitle().getPlainText()
                ));
            }
        }
        return resultEvents;
    }

}
