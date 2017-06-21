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

    /** 最大空闲连接数：空闲连接超过这个数，将回收 */
    private static int MAX_IDLE=300;
    /** 最小空闲连接数：空闲连接小于这个数，将创建新的连接 */
    private static int MIN_IDLE=5;
    /** 最大连接数：能够同时建立的最大连接个数 */
    private static int MAX_ACTIVE=600;
    /** 最大等待时间（毫秒）获取连接时间超过这个时间，将抛错 */
    private static int MAX_WAIT=1000;
    /** //使用连接时，检测连接是否成功 */
    boolean testOnBorrow=true;
    /**返回连接时，检测连接是否成功*/
    boolean testOnReturn=true;

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
     * @return
     */
    public static JedisPool getJedisPool(String redisHost,int redisPort,int maxActive,int minIdle,int maxIdle,int maxWait,boolean testOnBorrow,boolean testOnReturn){
        if (jedisPool==null){
            synchronized (JedisUtil.class){
                if (jedisPool==null){
                    JedisPoolConfig config = new JedisPoolConfig();
                    config.setMaxTotal(maxActive);
                    config.setMinIdle(minIdle);
                    config.setMaxIdle(maxIdle);
                    config.setMaxWaitMillis(maxWait);
                    config.setTestOnBorrow(testOnBorrow);
                    config.setTestOnReturn(testOnReturn);
                    jedisPool = new JedisPool(config, redisHost, redisPort);
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
