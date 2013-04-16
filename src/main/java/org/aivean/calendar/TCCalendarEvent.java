package org.aivean.calendar;

import java.util.Date;

/**
 * @author izaytsev
 *         3/31/12 2:54 PM
 */
public final class TCCalendarEvent implements Comparable<TCCalendarEvent>{
    private final Date startTime;
    private final Date endTime;
    private final String title;

    public TCCalendarEvent(Date startTime, Date endTime, String title) {
        if (startTime==null || endTime==null || title == null) {
            throw new IllegalArgumentException("None fields can be null!");
        }
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
    }

    @Override
    public int compareTo(TCCalendarEvent that) {
        if (!endTime.equals(that.endTime)) {
            return endTime.compareTo(that.endTime);
        }
        if (!startTime.equals(that.startTime)) {
            return startTime.compareTo(that.startTime);
        }
        if (!title.equals(that.title)) {
            return title.compareTo(that.title);
        }
        return 0;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TCCalendarEvent that = (TCCalendarEvent) o;

        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = startTime != null ? startTime.hashCode() : 0;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TCCalendarEvent{" +
                "title='" + title + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }
}
