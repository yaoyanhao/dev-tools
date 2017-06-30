package utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vector01.yao on 2017/5/18.
 *
 */
public class PropertiesUtil {
    private static final String DEFAULT = "default";
    private static Logger logger= LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 获取Properties文件中的属性
     * @param location 属性文件位置
     * @return
     */
    public static Map<String,String> resolveDbConfig(String location) {
        if (StringUtils.isBlank(location)){
            logger.error("resolve properties file error:location is null!");
            throw new RuntimeException("resolve properties file error:location is null!");
        }

        Map<String,String> propertyMap=new HashMap<String,String>();
        InputStream is = DataBaseHelper.class.getClassLoader().getResourceAsStream(location);
        InputStreamReader reader = null;
        BufferedReader br = null;
        if (is != null) {
            String line;
            try {
                reader = new InputStreamReader(is, "utf-8");
                br = new BufferedReader(reader);
                while ((line = br.readLine()) != null) {
                    if (StringUtils.isBlank(line)||line.startsWith("#")){
                        continue;
                    }
                    propertyMap.put(StringUtils.substringBefore(line, "="), StringUtils.substringAfter(line, "="));
                }
            } catch (IOException e) {
                logger.error("Could not load file from " + location + ": " + e.getMessage());
            } finally {
                IOUtil.closeStream(is, reader, br);
            }
        }
        return propertyMap;
    }

    /**
     * 获取系统变量
     * @param key
     * @return
     */
    public static String resolveSystemProperty(String key) {
        String value = null;
        try {
            value = System.getProperty(key);
        } catch (Throwable e) {
            logger.warn("Failed to get value of property: " + key, e);
        }

        if (null == value) {
            try {
                value = System.getenv(key);
            } catch (Exception e) {
                logger.warn("Failed to get value from env : " + key, e);
            }
            if (null == value) {
                value = DEFAULT;
            }
            return value;
        } else {
            return value;
        }
    }
}
