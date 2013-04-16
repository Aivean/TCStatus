package org.aivean.teamcity.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.LinkedList;
import java.util.List;

/**
 * @author izaytsev
 *         3/1/12 12:45 AM
 */
@XStreamAlias("builds")
public class Builds {

    @XStreamImplicit
    List<Build> builds = new LinkedList<Build>();

    public List<Build> getBuilds() {
        return builds;
    }

    public void setBuilds(List<Build> builds) {
        this.builds = builds;
    }
}
