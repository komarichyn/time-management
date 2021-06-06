package com.jc.tm.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

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
//        String rootPath = Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("")).getPath();
//        String appPropsPath = rootPath + APPLICATION_CONF;
        var properties = new Properties();
        try(var stream = this.getClass().getClassLoader().getResourceAsStream(APPLICATION_CONF)){
            properties.load(stream);
        } catch (FileNotFoundException e) {
            log.error("File not found:{} ", APPLICATION_CONF);
            log.error(e.getMessage(), e);
            return null;
        } catch (IOException e) {
            log.error("Something went wrong during reading the file {} by path {}", APPLICATION_CONF, "classpath");
            log.error(e.getMessage(), e);
            return null;
        }
        return properties;
    }
}