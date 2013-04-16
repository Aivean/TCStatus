package org.aivean.application;

import org.aivean.teamcity.API;
import org.aivean.teamcity.model.Build;

import java.util.Date;

/**
* @author izaytsev
*         4/9/12 1:36 PM
*/
class BuildStatusModel implements BuildStatus {
    private final String buildNumber;
    private final Integer elapsedSeconds;
    private final Integer estimate;
    private final long updateTime;

    private Build build;

    public BuildStatusModel(Build build) {
        buildNumber = build.getNumber();
        updateTime = System.currentTimeMillis();
        if (build.getRunningInfo() != null) {
            elapsedSeconds = build.getRunningInfo().getElapsedSeconds();
            estimate = build.getRunningInfo().getEstimatedTotalSeconds();
        } else {
            elapsedSeconds = null;
            estimate = null;
        }
        this.build = build;
    }


    public boolean update(API api) {
        try {
            this.build = api.getBuildById(build.getId());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void update(Build build) {
        if (build==null || !build.getId().equals(this.build.getId())) {
            throw new IllegalArgumentException("Trying to update build status from build with different id");
        }
        this.build = build;
    }

    @Override
    public String getBuildNumber() {
        return buildNumber;
    }

    @Override
    public Integer getEstimate() {
        return estimate;
    }

    @Override
    public long getUpdateTime() {
        return updateTime;
    }


    @Override
    public String getBuildType() {
        return build.getBuildType();
    }

    @Override
    public String getBuildTypeId() {
        return build.getBuildTypeId();
    }

    @Override
    public Integer getId() {
        return build.getId();
    }

    @Override
    public String getNumber() {
        return build.getNumber();
    }

    @Override
    public Date getStartDate() {
        return build.getStartDate();
    }

    @Override
    public org.aivean.teamcity.model.BuildStatus getStatus() {
        return build.getStatus();
    }

    @Override
    public String getStatusText() {
        return build.getStatusText();
    }


    /**
     * @return runningTime in milliseconds
     */
    @Override
    public long getRunningTime() {
        if (isRunning()) {
            return System.currentTimeMillis() - updateTime + elapsedSeconds *1000;
        }
        return 0;
    }

    @Override
    public long getTimeAfterCompletion() {
        if (!isRunning()) {
            if (elapsedSeconds == null) {
                return System.currentTimeMillis() - build.getFinishDate().getTime();
            } else {
                return System.currentTimeMillis() - updateTime + elapsedSeconds * 1000 - (build.getFinishDate()
                        .getTime() - build.getStartDate().getTime());
            }
        }
        return 0;
    }

    @Override
    public long getEstimatedRunningTimeSecs() {
        if (estimate != null) {
            return estimate;
        }
        return 0;
    }

    @Override
    public boolean isRunning() {
        return build.getRunning() && elapsedSeconds != null;
    }

    @Override
    public String getUser() {
        return build.getUser().getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuildStatusModel that = (BuildStatusModel) o;

        if (build.getId() != null ? !build.getId().equals(that.build.getId()) : that.build.getId() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return build != null ? build.getId().hashCode() : 0;
    }
}
