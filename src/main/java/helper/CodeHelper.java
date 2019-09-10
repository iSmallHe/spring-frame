package helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class CodeHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeHelper.class);

    public static String encodeURL(String source){
        String result = null;
        try {
            result = URLEncoder.encode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("CodeHelper encodeURL failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String decodeURL(String source){
        String result = null;
        try {
            result = URLDecoder.decode(source,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("CodeHelper decodeURL failure",e);
            throw new RuntimeException(e);
        }
        return result;
    }
}
