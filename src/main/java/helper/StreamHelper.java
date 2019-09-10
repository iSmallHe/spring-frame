package helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamHelper.class);

    public static String getString(InputStream is){
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            while((line = br.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("StreamHelper getString failure",e);
            throw new RuntimeException();
        }
        return sb.toString();
    }
}
