package org.aivean.teamcity.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author izaytsev
 *         3/1/12 12:52 AM
 */
@XStreamAlias("running-info")
public class RunningInfo {

    @XStreamAsAttribute
    Integer elapsedSeconds;

    @XStreamAsAttribute
    Integer estimatedTotalSeconds;

    public Integer getElapsedSeconds() {
        return elapsedSeconds;
    }

    public Integer getEstimatedTotalSeconds() {
        return estimatedTotalSeconds;
    }

    public void setEstimatedTotalSeconds(Integer estimatedTotalSeconds) {
        this.estimatedTotalSeconds = estimatedTotalSeconds;
    }
}
