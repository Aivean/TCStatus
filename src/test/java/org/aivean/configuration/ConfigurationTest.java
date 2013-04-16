package org.aivean.configuration;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author izaytsev
 *         3/1/12 12:34 AM
 */
public class ConfigurationTest {

    @Test
    public void testConfiguration() throws Exception {

        Configuration conf = new Configuration();
        conf.setBaseUrl("http://localhost:8111");
        conf.setUser("aivean");
        conf.setPass("integer6");
        conf.setRefreshIntervalSec(30);

        BuildConfiguration buildConfiguration = new BuildConfiguration();
        buildConfiguration.setBuildTypeId("bt2");
        buildConfiguration.setDisplayName("8.6.0");
        conf.getBuildConfigurations().add(buildConfiguration);

        buildConfiguration = new BuildConfiguration();
        buildConfiguration.setBuildTypeId("bt2");
        buildConfiguration.setDisplayName("trunk");
        conf.getBuildConfigurations().add(buildConfiguration);

       // System.out.println(Configuration.XSTREAM.toXML(conf));
        String s = Configuration.XSTREAM.toXML(conf);
        Configuration testConf = (Configuration) Configuration.XSTREAM.fromXML(s);
        Assert.assertEquals(conf.getBuildConfigurations().get(0).getDisplayName(),
                testConf.getBuildConfigurations().get(0).getDisplayName());

    }

    public void testLoad() throws Exception {
        Configuration load = Configuration.load();
        System.out.println(load.getCalendarUrl());
    }
}
