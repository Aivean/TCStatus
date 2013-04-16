package org.aivean.configuration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.aivean.util.XStreamFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author izaytsev
 *         3/1/12 12:24 AM
 */

@XStreamAlias("config")
public class Configuration {

    public final static XStream XSTREAM = XStreamFactory.getXStream(Configuration.class, BuildConfiguration.class,
            CalendarConfiguration.class);

    public static Configuration load() throws IOException {
        File file = new File("config.xml");
        InputStream ins = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String str;
        StringBuilder sb = new StringBuilder();
        while((str = reader.readLine()) != null) {
            sb.append(str);
        }
        ins.close();
        return (Configuration) XSTREAM.fromXML(sb.toString());
    }

    String baseUrl;
    String user;
    String pass;
    Integer refreshIntervalSec;
    Boolean fullScreen = true;
    Integer dimensionsRatio = 10;
    Integer showCompletedPersonalBuildSecs = 10;

    String calendarUrl;

    @XStreamAlias("calendar")
    CalendarConfiguration calendarConfig;

    @XStreamImplicit
    private List<BuildConfiguration> buildConfigurations = new LinkedList<BuildConfiguration>();

    public List<BuildConfiguration> getBuildConfigurations() {
        return buildConfigurations;
    }

    public void setBuildConfigurations(List<BuildConfiguration> buildConfigurations) {
        this.buildConfigurations = buildConfigurations;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getRefreshIntervalSec() {
        return refreshIntervalSec;
    }

    public void setRefreshIntervalSec(Integer refreshIntervalSec) {
        this.refreshIntervalSec = refreshIntervalSec;
    }

    public Boolean getFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(Boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public Integer getDimensionsRatio() {
        return dimensionsRatio;
    }

    public void setDimensionsRatio(Integer dimensionsRatio) {
        this.dimensionsRatio = dimensionsRatio;
    }

    public String getCalendarUrl() {
        return calendarUrl.trim();
    }

    public void setCalendarUrl(String calendarUrl) {
        this.calendarUrl = calendarUrl;
    }

    public CalendarConfiguration getCalendarConfig() {
        return calendarConfig;
    }

    public void setCalendarConfig(CalendarConfiguration calendarConfig) {
        this.calendarConfig = calendarConfig;
    }

    public Integer getShowCompletedPersonalBuildSecs() {
        return showCompletedPersonalBuildSecs;
    }

    public void setShowCompletedPersonalBuildSecs(Integer showCompletedPersonalBuildSecs) {
        this.showCompletedPersonalBuildSecs = showCompletedPersonalBuildSecs;
    }
}
