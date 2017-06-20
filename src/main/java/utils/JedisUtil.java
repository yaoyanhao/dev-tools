package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by vector01.yao on 2017/6/20.
 * Jedis工具类
 */
public class JedisUtil {
    private static String REDIS_HOST="10.199.201.229";
    private static int REDIS_PORT=6379;
    private static String REDIS_PWD=null;

    private static int MAX_IDLE=300;
    private static int MAX_ACTIVE=600;
    private static int MAX_WAIT=1000;
    private static int TIMEOUT=1000;

    private static volatile JedisPool jedisPool;
    private static volatile Jedis jedis;

    /**
     * 获取Jedis连接池
     * @param hostName host
     * @param port 端口号
     * @return
     */
    public static JedisPool getJedisPool(String hostName,int port){
        if (jedisPool==null){
            synchronized (JedisUtil.class){
                if (jedisPool==null){
                    JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
                    jedisPoolConfig.setMaxIdle(MAX_IDLE);
                    jedisPoolConfig.setMaxWaitMillis(MAX_WAIT);
                    jedisPoolConfig.setMaxTotal(MAX_ACTIVE);
                    jedisPool=new JedisPool(jedisPoolConfig,hostName,port,TIMEOUT);
                }
            }
        }
        return jedisPool;
    }

    /**
     * 获取Jedis连接池
     * @param redisHost
     * @param redisPort
     * @param maxActive
     * @param maxIdle
     * @param maxWait
     * @param testOnBorrow
     * @param timeOut
     * @return
     */
    public static JedisPool getJedisPool(String redisHost,int redisPort,int maxActive,int maxIdle,int maxWait,boolean testOnBorrow,int timeOut){
        if (jedisPool==null){
            synchronized (JedisUtil.class){
                if (jedisPool==null){
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(maxActive);
                    config.setMaxIdle(maxIdle);
                    config.setMaxWaitMillis(maxWait);
                    config.setTestOnBorrow(testOnBorrow);
                    jedisPool = new JedisPool(config, redisHost, redisPort, timeOut);
                }
            }
        }
        return jedisPool;
    }

    /**
     * 获取Jedis对象
     * @param hostName host
     * @param port 端口号
     * @return
     */
    public static Jedis getJedis(String hostName,int port){
        if (jedis!=null){
            return jedis;
        }
        return getJedisPool(hostName,port).getResource();
    }

    /**
     * 归还jedis连接
     * @param jedis jedis对象
     * @param hostName host
     * @param port 端口号
     */
    public static void releaseJedis(Jedis jedis,String hostName,int port){
        if (jedis!=null){
            getJedisPool(hostName,port).returnResource(jedis);
        }
    }

}
