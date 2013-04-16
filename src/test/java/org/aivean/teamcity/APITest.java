package org.aivean.teamcity;

import org.aivean.teamcity.model.Build;
import org.aivean.teamcity.model.Change;
import org.junit.Ignore;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author izaytsev
 *         3/1/12 1:09 AM
 */
@Ignore
public class APITest {
    API veltiAPI = new API("veltitest", "veltitest", "http://tc.velti.kyiv.cogniance.com:8111");
    API api = new API("test", "test", "http://localhost:8111");

    @Test
    public void testDownload() throws Exception {
        System.out.println(api.download("http://localhost:8111/httpAuth/app/rest/builds/?locator=running:any," +
                "buildType:bt2,count:2"));
    }

    @Test
    public void testGetLastBuild() throws Exception {
        for (int i=4765; i>=4282; i--) {
            try {
//                Build bt2 = api.getBuildById(i);
//                System.out.println(bt2.getHref());
                Build bt2 = null;
                try {
                    bt2 = veltiAPI.getBuildById(i);
                } catch (Exception ignore) {}
                if (bt2!=null) {
                LinkedList<Change> changesForBuild = veltiAPI.getChangesForBuild(i);
                System.out.println(i+" "+changesForBuild.size());
                } else {
                    System.out.println(i+" ignored");
                }
            } catch (Exception e) {
                System.err.println("Failed at buildNumber: "+i);
                e.printStackTrace();
                return;
            }
        }

    }

    @Test
    public void testGetBuildById() throws Exception {
        Build bt2 = api.getBuildById(4514);
        System.out.println(bt2.getHref());

    }

    @Test
    public void testVeltiGetBuildById() throws Exception {
        Build bt2 = veltiAPI.getBuildById(6250);
        //System.out.println(bt2.getHref());
    }

    @Test
    public void testGetLastStable() throws Exception {
        Build bt2 = api.getLastStableBuild("bt2");
        System.out.println(bt2.getHref());
    }


    @Test
    public void testGetRunningPersonalBuilds() throws Exception {
        List<Build> bt2 = api.getRunningPersonalBuilds("bt2");
        System.out.println(bt2.size());
    }


    @Test
    public void testChanges() throws Exception {
        LinkedList<Change> changesForBuild = veltiAPI.getChangesForBuild(4607);
        System.out.println(changesForBuild.size());

    }
}
