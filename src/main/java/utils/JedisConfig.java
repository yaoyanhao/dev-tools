package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by vector01.yao on 2017/6/26.
 * Jedis连接池配置
 */
public class JedisConfig {
    private Logger logger= LoggerFactory.getLogger(JedisConfig.class);

    //默认配置文件地址
    private static String configFilePath="redis.properties";

    /** redis服务器IP  */
    public static String REDIS_HOST;
    /** redis服务器端口号  */
    public static int REDIS_PORT;
    /** redis服务器端口号  */
    public static String REDIS_PWD;

    /** 最大空闲连接数：空闲连接超过这个数，将回收 */
    public static int MAX_IDLE;
    /** 最大连接数：能够同时建立的最大连接个数 */
    public static int MAX_ACTIVE;
    /** 最大等待时间（毫秒）获取连接时间超过这个时间，将抛错 */
    public static int MAX_WAIT;
    /** 使用连接时，检测连接是否成功 */
    public static boolean TEST_ON_BORROW;
    /**返回连接时，检测连接是否成功*/
    public static boolean TEST_ON_RETURN;

    static {
        Map<String,String> redisConfigMap= PropertiesUtil.resolveDbConfig(configFilePath);
        if (redisConfigMap.size()==0){
            throw new RuntimeException("resolve property file error! property map is empty");
        }
        REDIS_HOST=redisConfigMap.get("redis.host");
        REDIS_PORT=Integer.parseInt(redisConfigMap.get("redis.port"));
        REDIS_PWD=redisConfigMap.get("redis.password");
        MAX_ACTIVE=Integer.valueOf(redisConfigMap.get("redis.maxActive"));
        MAX_IDLE=Integer.valueOf(redisConfigMap.get("redis.maxIdle"));
        MAX_WAIT=Integer.valueOf(redisConfigMap.get("redis.maxWait"));
        TEST_ON_BORROW=Boolean.parseBoolean("jedis.jedis.testOnBorrow");
        TEST_ON_RETURN=Boolean.parseBoolean("redis.testOnReturn");
    }

}
