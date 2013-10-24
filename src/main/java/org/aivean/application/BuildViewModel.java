package org.aivean.application;

import org.aivean.application.event.FailedBuildEvent;
import org.aivean.configuration.BuildConfiguration;
import org.aivean.teamcity.API;
import org.aivean.teamcity.model.Build;
import org.aivean.teamcity.model.BuildStatus;
import org.aivean.teamcity.model.Change;

import java.util.*;

/**
 * @author izaytsev
 *         3/1/12 2:03 AM
 *         <p/>
 *         Stores statuses of the one build type:
 *         <ul>
 *         <li>current build,</li>
 *         <li>last finished build,</li>
 *         <li>current and recent personal builds</li>
 *         </ul>
 *         Can be easily accessed from render cycle.
 *         Status is updated from TCStatusRefreshTimer thread
 */
public class BuildViewModel {

    BuildConfiguration configuration;
    LinkedHashSet<BuildStatusModel> runningPersonalBuilds = new LinkedHashSet<BuildStatusModel>(5);

    private BuildStatusModel currentBuild;
    private BuildStatusModel lastFinishedBuild;

    public BuildViewModel(BuildConfiguration configuration) {
        this.configuration = configuration;
    }

    public org.aivean.application.BuildStatus getCurrentBuild() {
        return currentBuild;
    }

    public org.aivean.application.BuildStatus getLastFinishedBuild() {
        return lastFinishedBuild;
    }

    public boolean isHidden() {
        return this.configuration.getHidden() && (!this.isInitialized() || this.getCurrentBuild().getStatus() == BuildStatus.SUCCESS &&
                !this.getCurrentBuild().isRunning() && this.getPersonalBuildsCount() == 0);
    }

    public String getDisplayName() {
        return configuration.getDisplayName();
    }

    public String getBuildTypeId() {
        return configuration.getBuildTypeId();
    }


    public int getBuildNumber() {
        if (currentBuild != null) {
            try {
                return Integer.valueOf(currentBuild.getNumber());
            } catch (NumberFormatException ignored) {
            }
        }
        return 0;
    }

    public BuildStatus getLastStableBuildStatus() {
        if ( lastFinishedBuild != null) {
            return lastFinishedBuild.getStatus();
        } else {
            return null;
        }
    }


    public String getStatusText() {
        if (currentBuild != null) {
            return this.currentBuild.getStatusText();
        }
        return null;
    }

    public boolean isInitialized() {
        return currentBuild != null;
    }


    public void updateFromBuildStatus(API api) {

        List<Build> updateRunningPersonalBuilds;
        Build updateLastStableBuild;
        Build updateCurrentBuild;
        try {
            updateRunningPersonalBuilds = api.getRunningPersonalBuilds(getBuildTypeId());
            updateLastStableBuild = api.getLastStableBuild(getBuildTypeId());
            updateCurrentBuild = api.getLastBuild(getBuildTypeId());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Adding failed build event
        if (updateLastStableBuild != null && this.lastFinishedBuild != null &&
                !updateLastStableBuild.getStatus().equals(BuildStatus.SUCCESS) && BuildStatus.SUCCESS.equals(this
                .lastFinishedBuild.getStatus())) {

            Set<String> failers = getFailers(api, updateLastStableBuild.getId());
            Application.getInstance().addEvent(new FailedBuildEvent(getDisplayName(),
                    new LinkedList<String>(failers)));
        }

        if (updateLastStableBuild != null) {
            if (this.lastFinishedBuild == null || !this.lastFinishedBuild.getId().equals(updateLastStableBuild.getId
                    ())) {
                this.lastFinishedBuild = new BuildStatusModel(updateLastStableBuild);
            } else {
                this.lastFinishedBuild.update(updateLastStableBuild);
            }
        }

        if (updateCurrentBuild != null) {
            if (this.currentBuild == null || !this.currentBuild.getId().equals(updateCurrentBuild.getId())) {
                this.currentBuild = new BuildStatusModel(updateCurrentBuild);
            } else {
                this.currentBuild.update(updateCurrentBuild);
            }
        }


        final int showPersonalBuildsTime = Application.getInstance().getConfiguration()
                .getShowCompletedPersonalBuildSecs() * 1000;
        //updating personal builds
        HashMap<Integer, BuildStatusModel> oldPersonalBuilds = new HashMap<Integer, BuildStatusModel>();
        for (BuildStatusModel personalBuild : runningPersonalBuilds) {
            oldPersonalBuilds.put(personalBuild.getId(), personalBuild);
        }
        for (Build updatedBuild : updateRunningPersonalBuilds) {
            final BuildStatusModel buildStatusModel = oldPersonalBuilds.get(updatedBuild.getId());
            if (buildStatusModel != null) {
                buildStatusModel.update(api);
                oldPersonalBuilds.remove(updatedBuild.getId());
            } else {
                try {
                    Build extendedBuild = api.getBuildById(updatedBuild.getId());
                    runningPersonalBuilds.add(new BuildStatusModel(extendedBuild));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        for (BuildStatusModel oldCompletedPersonalBuild : oldPersonalBuilds.values()) {
            boolean runningAndUpdated = false;
            if (oldCompletedPersonalBuild.isRunning()) {
                runningAndUpdated = oldCompletedPersonalBuild.update(api);
            }
            if (!runningAndUpdated || oldCompletedPersonalBuild.getTimeAfterCompletion() > showPersonalBuildsTime) {
                runningPersonalBuilds.remove(oldCompletedPersonalBuild);
            }
        }
    }

    public Set<String> getFailers(API api, int buildId) {
        Set<String> failers = new HashSet<String>();
        try {
            LinkedList<Change> changesForBuild = api.getChangesForBuild(buildId);
            for (Change change : changesForBuild) {
                failers.add(change.getUsername());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return failers;
    }

    public BuildStatusModel getlastFinishedBuild() {
        return lastFinishedBuild;
    }

    public int getPersonalBuildsCount(){
        return runningPersonalBuilds.size();
    }

    public org.aivean.application.BuildStatus[] getPersonalBuilds() {
        return runningPersonalBuilds.toArray(new org.aivean.application.BuildStatus[runningPersonalBuilds.size()]);
    }

}
