package org.aivean.application.drawer.buildstatus;

import org.aivean.application.Application;
import org.aivean.application.BuildStatus;
import org.aivean.application.BuildViewModel;
import org.aivean.application.drawer.ViewPainter;
import org.aivean.calendar.TCCalendarEvent;
import org.aivean.util.FontAlign;
import org.aivean.util.StringUtil;

import java.awt.*;

import static org.aivean.teamcity.model.BuildStatus.ERROR;
import static org.aivean.teamcity.model.BuildStatus.FAILURE;
import static org.aivean.teamcity.model.BuildStatus.SUCCESS;

/**
 * @author izaytsev
 *         3/1/12 10:55 PM
 */
public class TCBuildStatusViewPainter implements ViewPainter {

    public TCBuildStatusViewPainter() {
    }

//    static long counter = 0;

    private static class BuildStatusResult {
        final PaintedContainer inlineStatus;
        final ResizablePaintedRow rowStatus;

        private BuildStatusResult(String statusText) {
            if (statusText==null || isTrivialStatus(statusText)) {
                inlineStatus = null;
                rowStatus = null;
            } else {
                PaintedContainer statusRez;
                statusRez = TestsCustomElement.fromString(statusText);
                if (statusRez == null) {
                    statusRez = CheckstyleCustomElement.fromString(statusText);
                }
                if (statusRez != null) {
                    if ((statusRez instanceof CheckstyleCustomElement) && ((CheckstyleCustomElement) statusRez).isWide
                            ()) {
                        inlineStatus = null;
                        rowStatus = new PaintedContainerRow(statusRez, 0.7);
                    } else {
                        inlineStatus = statusRez;
                        rowStatus = null;
                    }
                } else {
                    if (statusText.length() <= 10) {
                        rowStatus = null;
                        inlineStatus = new BuildStatusRow(statusText);
                    } else {
                        rowStatus = new BuildStatusRow(statusText);
                        inlineStatus = null;
                    }
                }
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics, int w, int h) {
        final int showCompletedPersonalBuildsMs = Application.getInstance().getConfiguration()
                .getShowCompletedPersonalBuildSecs() * 1000;

        graphics.setColor(Color.white);
        graphics.setBackground(Color.white);
        graphics.fillRect(0, 0, w, h);

        graphics.setColor(Color.black);
        BuildViewModel[] builds = Application.getInstance().getBuilds();



        int hiddenCount = 0;
        BuildStatusWindow window = new BuildStatusWindow();

        TCCalendarEvent nextCalendarEvent = Application.getInstance().getNextCalendarEvent();
        if (nextCalendarEvent != null) {
            window.addRow(new IncomingEventRow(nextCalendarEvent));
            window.addRow(new LineSeparator(Color.WHITE, 0.7));
        }

        for (int i = 0; i < builds.length; i++) {
            final BuildViewModel build = builds[i];
            BuildStatus currentBuildStatus = build.getCurrentBuild();

            if (!build.isInitialized() || currentBuildStatus == null || build.isHidden()) {
                hiddenCount ++;
                continue;
            }
            ResizablePaintedRowSet buildSet = new ResizablePaintedRowSet();

            TextElement buildNameTextEl = new TextElement(Color.BLACK, build.getDisplayName(), FontAlign.RIGHT);
            PaintedContainer rightEl = getRightNumber(currentBuildStatus);

            String statusText = currentBuildStatus.getStatusText();
            BuildStatusResult buildStatusResult = new BuildStatusResult(statusText);

            buildSet.addRow(new BuildNameRow(getIcon(build), buildNameTextEl, buildStatusResult.inlineStatus, rightEl));
            if (buildStatusResult.rowStatus!=null){
                buildSet.addRow(new LineSeparator(Color.WHITE, 0.3));
                buildSet.addRow(buildStatusResult.rowStatus);
            }

            if (build.getPersonalBuildsCount()>0) {
                final BuildStatus[] personalBuilds = build.getPersonalBuilds();
                for (BuildStatus personalBuild : personalBuilds) {
                    if (!personalBuild.isRunning() && personalBuild.getTimeAfterCompletion() >
                            showCompletedPersonalBuildsMs) {
                        continue;
                    }
                    final String personalBuildStatusText = personalBuild.getStatusText();

                    buildSet.addRow(new LineSeparator(Color.WHITE, 0.2));
                    //buildSet.addRow(new PersonalBuildRow(build.getPersonalBuildsCount()));
                    Color color = Color.BLACK;
                    if (personalBuild.getStatus() == ERROR || personalBuild.getStatus() == FAILURE) {
                        color = Color.RED;
                    } else if (!personalBuild.isRunning() && personalBuild.getStatus() == SUCCESS) {
                        color = Color.GREEN;
                    } else {
                        color = Color.BLACK;
                    }

                    PaintedContainer personalBuildRightEl = getRightNumber(personalBuild);
                    TextElement personalBuildName = new TextElement(color, personalBuild.getUser(),
                            FontAlign.RIGHT);
                    BuildStatusResult personalStatus = new BuildStatusResult(personalBuildStatusText);
                    buildSet.addRow(new PersonalBuildNameRow(personalBuild.getStatus(), personalBuildName,
                            personalStatus.inlineStatus, personalBuildRightEl));
                }
            }

            window.addRow(buildSet);
            if (i!=builds.length-1) {
                //window.addRow(new LineSeparator(Color.LIGHT_GRAY, 0.4));
                window.addRow(new LineSeparator(Color.WHITE, 0.7));
            }
        }

        if (hiddenCount>0) {
            window.addRow(new LineSeparator(Color.WHITE, 0.7));
            window.addRow(new HiddenBuildsRow(hiddenCount+" builds hidden"));
        }
//        window.addRow(new RectangularPaintedEl(""+counter++));


        double shownBuilds = window.getYWeight();
        if (shownBuilds == 0) {
            shownBuilds = 1;
        }

        final int WIDTH_HEIGHT_D = Application.getInstance().getConfiguration().getDimensionsRatio();
        double preferedWidth = (w - w / 20);
        double preferedHeight = (h - (h / 10)) / shownBuilds;

        double widthFromHeight = preferedHeight * WIDTH_HEIGHT_D;
        double heightFromWidth = preferedWidth / WIDTH_HEIGHT_D;


        double usedHeight, usedWidth;
        if (widthFromHeight <= preferedWidth) { // using dimensions based on height
            usedWidth = widthFromHeight;
            usedHeight = preferedHeight;
        } else {
            usedHeight = heightFromWidth;
            usedWidth = preferedWidth;
        }

        double startX = w / 2 - usedWidth / 2;
        double startY = h / 2 - (usedHeight * shownBuilds) / 2;
        double totalHeight = usedHeight * shownBuilds;

        window.paint(graphics, startX, startY, usedWidth, totalHeight);
    }

    private PaintedContainer getRightNumber(BuildStatus currentBuildStatus) {
        PaintedContainer rightEl = null;
        if (currentBuildStatus.isRunning()) {
            long buildRunningTimeSec = currentBuildStatus.getRunningTime() / 1000;
            String strTime = StringUtil.formatTimeSec(buildRunningTimeSec);
            long estimatedRunningTimeSecs = currentBuildStatus.getEstimatedRunningTimeSecs();
            String strEstimate = StringUtil.formatTimeSec(estimatedRunningTimeSecs);
            rightEl = new RunningTimeElement(strTime, strEstimate, buildRunningTimeSec >= estimatedRunningTimeSecs);
        } else {
           rightEl = new TextElement(Color.DARK_GRAY, "#"+currentBuildStatus.getBuildNumber(), FontAlign.LEFT);
        }
        return rightEl;
    }


    private static boolean isTrivialStatus(String status) {
        return status == null || status.isEmpty() || "Success".equalsIgnoreCase(status) || "Failure".equalsIgnoreCase
                (status);
    }

    IconElement getIcon(BuildViewModel build) {
        if (!build.isInitialized()) {
           return IconElement.UNKNOWN;
        } else if (build.getCurrentBuild().isRunning()) {
            if (build.getLastStableBuildStatus().equals(SUCCESS)) {
                return IconElement.RUNNING_GREEN;
            } else {
                return IconElement.RUNNING_RED;
            }
        } else {
            switch (build.getCurrentBuild().getStatus()) {
                case SUCCESS:
                    return IconElement.SUCCESS;
                case ERROR:
                case FAILURE:
                    return IconElement.ERROR;
                default:
                    return IconElement.UNKNOWN;
            }
        }
    }

}
