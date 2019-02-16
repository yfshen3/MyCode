package org.lf.admin.api.baseapi.config;

import org.lf.admin.api.baseapi.enums.DirectoryEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Constants {

    public static String appName;
    public static String dataHome;
    public static String virtualDirName;
    public static final String BASEURL = "baseUrl";
    public static final String HTML_TITLE_PREFIX = "htmlTitlePrefix";
    public static final String HTML_TITLE = "htmlTitle";
    public static final String SESSION_ACCOUNT = "session_account";

    @Value("${gpbio.appName:tcm}")
    public void setAppName(String appName) {
        Constants.appName = appName;
    }

    @Value("${app.dataHome:D://tcm}")
    public void setDataHome(String dataHome) {
        Constants.dataHome = dataHome;
    }

    @Value("${app.virtualDir:/tcm/temp}")
    public void setVirtualDirName(String virtualDirName) {
        Constants.virtualDirName = virtualDirName;
    }

    public static File getHomeDir(DirectoryEnum dir) {
        File home = new File(Constants.dataHome, dir.name());
        if (!home.exists()) {
            home.mkdir();
        }
        return home;
    }
}