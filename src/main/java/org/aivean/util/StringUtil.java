package org.aivean.util;

/**
 * @author izaytsev
 *         3/1/12 11:46 AM
 */
public class StringUtil {
    
    public static String formatTimeSec(long timeSec) {
        long timeMs  = timeSec;
        long hours = timeMs / 3600; timeMs %= 3600;
        long mins = timeMs / 60; timeMs %= 60;
        long secs = timeMs;
        StringBuilder sb = new StringBuilder();
        if  (hours >0) {
            sb.append(hours);
            sb.append(":");
        }
        if  (mins >0) {
            if (hours>0) {
                sb.append(String.format("%02d", mins));
            } else {
                sb.append(mins);
            }
            sb.append(":");
        }

        if (mins>0) {
            sb.append(String.format("%02d", secs));
        } else {
            sb.append(secs);
        }



        return sb.toString();
    }
}
