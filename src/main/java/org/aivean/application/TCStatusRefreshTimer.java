package org.aivean.application;

import org.aivean.teamcity.API;
import org.aivean.teamcity.model.Build;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author izaytsev
 *         2/29/12 11:47 PM
 *
 *  This timer refreshes statuses of the ViewModel with the data
 *  retrieved via TC API
 */
public class TCStatusRefreshTimer extends Timer {

    int interval;
    private API api;

    public TCStatusRefreshTimer(API api, int interval) {
        this.interval = interval;
        this.api = api;
        schedule(new RefreshTimerTask(), 10);
    }
    

    class RefreshTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                BuildViewModel[] builds = Application.getInstance().getBuilds();
                for (int i = 0; i < builds.length; i++) {
                    long time = System.currentTimeMillis();
                    BuildViewModel build = builds[i];
                    build.updateFromBuildStatus(api);
                    //System.out.println(build.getDisplayName() + (System.currentTimeMillis()-time));
                }
            } finally {
                schedule(new RefreshTimerTask(), interval);
            }
        }
    }



}
