package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by vector01.yao on 2017/5/19.
 */
public class IOUtil {

    private static Logger logger= LoggerFactory.getLogger(IOUtil.class);


    public static void closeStream(InputStream is, InputStreamReader reader, BufferedReader br) {
        try {
            is.close();
            if (reader != null) {
                reader.close();
            }
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            logger.error("Could not close file from " + e.getMessage());
        }
    }
}
