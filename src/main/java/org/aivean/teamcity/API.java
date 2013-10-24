package org.aivean.teamcity;

import com.thoughtworks.xstream.XStream;

import org.aivean.teamcity.model.*;
import org.aivean.util.XStreamFactory;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;

/**
 * @author izaytsev
 *         3/1/12 12:46 AM
 *
 *  This class incapsulates work with TC API
 */
public class API {

    public static final XStream XSTREAM = XStreamFactory.getXStream(Builds.class, Build.class, RunningInfo.class,
            Change.class, Changes.class);

    String user;
    String password;
    String baseUrl;

    /**
     *
     * @param user
     * @param password
     * @param baseUrl http://localhost:8111
     */
    public API(String user, String password, String baseUrl) {
        this.user = user;
        this.password = password;
        this.baseUrl = baseUrl;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    class MyAuthenticator extends Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return (new PasswordAuthentication(user, password.toCharArray()));
        }
    }

    public String download(String urlString) throws Exception {
        Authenticator.setDefault(new MyAuthenticator());
        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();
        InputStream ins = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String str;
        StringBuilder sb = new StringBuilder();
        while((str = reader.readLine()) != null) {
            sb.append(str);
        }
        ins.close();
        return sb.toString();
    }


    private Build getFirstBuildForQuery(String query) {
        try {
            List<Build> builds = getBuildsForQuery(query);
            if (builds.size()==0) {
                return null;
            }
            Build build = builds.iterator().next();
            if (build.getId() != null) {
                build = getBuildById(build.getId());
            }
            return build;
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    private List<Build> getBuildsForQuery(String query) throws ApiException {
        try {
            String str = download(getBaseUrl() + "/builds/" + query);
            return ((Builds) XSTREAM.fromXML(str)).getBuilds();
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }


    public Build getBuildById(Integer buildId) throws ApiException {
        try {
            String str;
            str = download(getBaseUrl() + "/builds/id:" + buildId);
            return (Build) XSTREAM.fromXML(str);
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    public Build getLastStableBuild(String buildTypeId) {
        return getFirstBuildForQuery("?locator=running:false,canceled:false,personal:false," +
                "buildType:" + buildTypeId + ",count:1");
    }


    public List<Build> getRunningPersonalBuilds(String buildTypeId) {
        return getBuildsForQuery("?locator=running:true,canceled:false,personal:true," +
                "buildType:" + buildTypeId );
    }

    public Build getLastBuild(String buildTypeId) {
        return getFirstBuildForQuery("?locator=running:any,personal:false,canceled:false," +
                "buildType:" + buildTypeId + ",count:1");
    }

    public LinkedList<Change> getChangesForBuild(Integer buildId) {
        try {
            String str;
            str = download(getBaseUrl() + "/changes?build=id:" + buildId);
            Changes changes = (Changes) XSTREAM.fromXML(str);

            LinkedList<Change> result = new LinkedList<Change>();
            for (Change change : changes.getChanges()) {
                str = download(getHostUrl() + change.getHref());
                result.add((Change) XSTREAM.fromXML(str));
            }
            return result;
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }

    public String getHostUrl() {
        URL url;
        try {
            url = new URL(getBaseUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return format("%s://%s:%s", url.getProtocol(), url.getHost(), url.getPort());
    }

}
