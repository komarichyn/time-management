package com.jc.tm.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

//made this class as singleton
public class LoadPropertiesHelper {
    private static final String APPLICATION_CONF = "application.properties";
    private static LoadPropertiesHelper helper;

    private LoadPropertiesHelper() {
    }

    public static final LoadPropertiesHelper getInstance() {
        if (helper == null) {
            helper = new LoadPropertiesHelper();
        }
        return helper;
    }

    public Properties loadProperties() {
        String rootPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath();
        String appPropsPath = rootPath + APPLICATION_CONF;
        var properties = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(appPropsPath);
            properties.load(stream);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Something went wrong during reading file " + APPLICATION_CONF);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return properties;
    }
}