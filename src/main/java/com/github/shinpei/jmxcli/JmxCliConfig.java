package com.github.shinpei.jmxcli;

import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

public class JmxCliConfig {
    private static final String APPLICATION_VERSION = "application.version";
    static final public String getVersion() {
        Properties appProperties = new Properties();
        try {
            InputStream ins = Resources.getResource("app.properties").openStream();
            appProperties.load(ins);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return appProperties.getProperty(APPLICATION_VERSION);
    }


}
