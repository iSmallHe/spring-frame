package helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHelper.class);

    private static final ObjectMapper om = new ObjectMapper();

    public static String beanToJson(Object bean){
        String result = null;
        if(bean == null)
            return result;
        try {
            result = om.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            LOGGER.error("JsonHelper beanToJson failure",e);
            throw new RuntimeException();
        }
        return result;
    }

    public static <T> T jsonToBean(String json,Class<T> clazz){
        T t = null;
        if(StringUtils.isBlank(json)){
            return t;
        }
        try {
            t = om.readValue(json,clazz);
        } catch (IOException e) {
            LOGGER.error("JsonHelper jsonToBean failure",e);
            throw new RuntimeException();
        }
        return t;
    }
}
