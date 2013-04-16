package org.aivean.teamcity.model;

/**
 * @author izaytsev
 *         4/17/12 11:38 PM
 */
public class User {
    String href;
    Integer id;
    String username;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
