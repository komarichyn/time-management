package com.jc.tm.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

//made this class as singleton
public class LoadPropertiesHelper {

    private static final String APPLICATION_CONF = "application.properties";

    private static LoadPropertiesHelper helper;
    private LoadPropertiesHelper(){

    }

    public static final LoadPropertiesHelper getInstance(){
        if(helper == null){
            helper = new LoadPropertiesHelper();
        }
        return helper;
    }

    public Properties loadProperties() {
        //classpath location
        String rootPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("")).getPath();
        //working directory location
//        String rootPath = Path.of("").toAbsolutePath().toString();
        String appPropsPath = rootPath + APPLICATION_CONF;
        var properties = new Properties();
        try {
            //fixme, this constructor can throws filenotfound execption, but we do not handle that case
            var stream = new FileInputStream(appPropsPath);
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
            //fixme there is should be final block for close reading stream
        }


        return properties;
    }
}