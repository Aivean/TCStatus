package org.aivean.configuration;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author izaytsev
 *         3/1/12 12:31 AM
 */

@XStreamAlias("build")
public class BuildConfiguration {
    
    private String displayName;
    private String buildTypeId;
    private Boolean hidden = false;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBuildTypeId() {
        return buildTypeId;
    }

    public void setBuildTypeId(String buildTypeId) {
        this.buildTypeId = buildTypeId;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
