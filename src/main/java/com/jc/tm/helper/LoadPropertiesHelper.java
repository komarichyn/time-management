package com.jc.tm.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

//made this class as singleton
@Slf4j
public class LoadPropertiesHelper {
    private static final String APPLICATION_CONF = "application.properties";

    private static LoadPropertiesHelper helper;

    private LoadPropertiesHelper() {
    }

    public static LoadPropertiesHelper getInstance() {
        if (helper == null) {
            helper = new LoadPropertiesHelper();
        }
        return helper;
    }

    /**
     * load properties
     * @return loaded properties or null if some error happened
     */
    public Properties loadProperties() {
        String rootPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath();
        String appPropsPath = rootPath + APPLICATION_CONF;
        var properties = new Properties();
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(appPropsPath);
            properties.load(stream);
        } catch (FileNotFoundException e) {
            log.error("File not found:{} ", appPropsPath);
            log.error(e.getMessage(), e);
            return null;
        } catch (IOException e) {
            log.error("Something went wrong during reading file {} by path {}", APPLICATION_CONF, rootPath);
            log.error(e.getMessage(), e);
            return null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return properties;
    }
}