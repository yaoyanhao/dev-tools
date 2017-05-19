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
public class PropertitesUtil {

    private static Logger logger= LoggerFactory.getLogger(PropertitesUtil.class);

    /**
     * 获取properties内容
     *
     * @param location 属性文件位置
     * @return String
     */
    public static String resolveDbConfig(String location) {
        InputStream is = DataBaseHelper.class.getClassLoader().getResourceAsStream(location);
        InputStreamReader reader = null;
        BufferedReader br = null;
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                reader = new InputStreamReader(is, "utf-8");
                br = new BufferedReader(reader);
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                logger.error("Could not load file from " + location + ": " + e.getMessage());
            } finally {
                IOUtil.closeStream(is, reader, br);
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * properties转map
     *
     * @param pro
     * @return Map<String, Object>
     */
    public static Map<String, Object> propertiesToMap(String pro) {
        if (StringUtils.isBlank(pro)) {
            return new HashMap<String, Object>();
        }
        String[] strArray = pro.split("\n");
        Map<String, Object> result = new HashMap<String, Object>();
        for (String str : strArray) {
            result.put(StringUtils.substringBefore(str, "="), StringUtils.substringAfter(str, "="));
        }
        return result;
    }
}
