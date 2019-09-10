package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtil.class);

    public static Properties loadProperties(String fileName){
        Properties prop = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is ==  null){
                throw new FileNotFoundException(fileName + "file not found");
            }
            prop = new Properties();
            prop.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties failed!",e);
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failed",e);
                }
            }
        }
        return prop;
    }

    public static String getString(Properties prop , String name){
        return getString(prop,name,null);
    }

    public static String getString(Properties prop , String name , String defaultValue){
        String value = defaultValue;
        if(prop.containsKey(name)){
            value = prop.getProperty(name);
        }
        return value;
    }

    public static Integer getInt(Properties prop , String name){
        return getInt(prop,name,null);
    }

    public static Integer getInt(Properties prop , String name , Integer defaultValue){
        Integer value = defaultValue;
        if(prop.containsKey(name)){
            value = Integer.valueOf(prop.getProperty(name));
        }
        return value;
    }
}
