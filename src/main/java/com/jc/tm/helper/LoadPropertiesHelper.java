package com.jc.tm.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadPropertiesHelper {

    public static Properties loadProperties() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appPropsPath = rootPath + "application.properties";
        Properties applicationProperties = new Properties();
        try {
            applicationProperties.load(new FileInputStream(appPropsPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applicationProperties;
    }
}