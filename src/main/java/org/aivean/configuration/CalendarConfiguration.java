package org.aivean.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author izaytsev
 *         4/2/12 12:23 PM
 */
@XStreamAlias("calendar")
public class CalendarConfiguration {
    String user;
    String password;
    String url;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
