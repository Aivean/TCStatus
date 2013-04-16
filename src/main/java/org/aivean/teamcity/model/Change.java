package org.aivean.teamcity.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author izaytsev
 *         3/12/12 1:19 AM
 */
@XStreamAlias("change")
public class Change {

    @XStreamAsAttribute
    String username;

    @XStreamAsAttribute
    Integer id;

    @XStreamAsAttribute
    String href;

    String comment;
    String files;
    String user;

    public String getUsername() {
        return username;
    }

    public Integer getId() {
        return id;
    }

    public String getHref() {
        return href;
    }
}
