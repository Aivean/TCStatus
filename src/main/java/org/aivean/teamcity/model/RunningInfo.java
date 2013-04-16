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
    Integer percentageComplete;

    @XStreamAsAttribute
    Integer elapsedSeconds;

    @XStreamAsAttribute
    Boolean probablyHanging = false;

    @XStreamAsAttribute
    Integer estimatedTotalSeconds;

    @XStreamAsAttribute
    String currentStageText;

    public Integer getPercentageComplete() {
        return percentageComplete;
    }

    public void setPercentageComplete(Integer percentageComplete) {
        this.percentageComplete = percentageComplete;
    }

    public Integer getElapsedSeconds() {
        return elapsedSeconds;
    }

    public void setElapsedSeconds(Integer elapsedSeconds) {
        this.elapsedSeconds = elapsedSeconds;
    }

    public Boolean getProbablyHanging() {
        return probablyHanging;
    }

    public void setProbablyHanging(Boolean probablyHanging) {
        this.probablyHanging = probablyHanging;
    }

    public Integer getEstimatedTotalSeconds() {
        return estimatedTotalSeconds;
    }

    public void setEstimatedTotalSeconds(Integer estimatedTotalSeconds) {
        this.estimatedTotalSeconds = estimatedTotalSeconds;
    }

    public String getCurrentStageText() {
        return currentStageText;
    }

    public void setCurrentStageText(String currentStageText) {
        this.currentStageText = currentStageText;
    }
}
