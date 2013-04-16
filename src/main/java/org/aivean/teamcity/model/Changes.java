package org.aivean.teamcity.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.LinkedList;

/**
 * @author izaytsev
 *         3/12/12 1:19 AM
 */
@XStreamAlias("changes")
public class Changes {

    @XStreamImplicit
    LinkedList<Change> changes = new LinkedList<Change>();

    public LinkedList<Change> getChanges() {
        return changes;
    }
}
