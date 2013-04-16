package org.aivean.application;

import java.util.Date;

/**
 * @author izaytsev
 *         4/17/12 9:25 PM
 */
public interface BuildStatus {
    String getBuildNumber();

    Integer getEstimate();

    long getRunningTime();

    boolean isRunning();

    long getUpdateTime();

    String getBuildType();

    String getBuildTypeId();

    Integer getId();

    String getNumber();

    Date getStartDate();

    org.aivean.teamcity.model.BuildStatus getStatus();

    String getStatusText();

    long getTimeAfterCompletion();

    long getEstimatedRunningTimeSecs();

    String getUser();
}
