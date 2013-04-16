package org.aivean.application;

import org.aivean.teamcity.API;
import org.aivean.teamcity.model.Build;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author izaytsev
 *         4/18/12 1:04 AM
 */
@Ignore
public class BuildStatusModelTest {

    API veltiAPI = new API("veltitest", "veltitest", "http://tc.velti.kyiv.cogniance.com:8111");

    @Test
    public void testVeltiGetBuildById() throws Exception {
        Build bt2 = veltiAPI.getBuildById(6255);
        final BuildStatusModel buildStatusModel = new BuildStatusModel(bt2);
        System.out.println(buildStatusModel.getTimeAfterCompletion() / 1000);
    }


}
