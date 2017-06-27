package utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by vector01.yao on 2017/6/20.
 * Jedis工具类
 */
public class JedisUtil {

    private static int TIMEOUT=1000;

    private static volatile JedisPool jedisPool;
    private static volatile Jedis jedis;

    private static class JedisPoolHolder{
        public static JedisPool getJedisPool(){
            JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
            jedisPoolConfig.setMaxIdle(JedisConfig.MAX_IDLE);
            jedisPoolConfig.setMaxTotal(JedisConfig.MAX_ACTIVE);
            jedisPoolConfig.setMaxWaitMillis(JedisConfig.MAX_WAIT);
            jedisPoolConfig.setTestOnBorrow(JedisConfig.TEST_ON_BORROW);
            jedisPoolConfig.setTestOnReturn(JedisConfig.TEST_ON_RETURN);
            jedisPool=new JedisPool(jedisPoolConfig,JedisConfig.REDIS_HOST,JedisConfig.REDIS_PORT,TIMEOUT);
            return jedisPool;
        }
    }

    /**
     *
     * @return
     */
    public static JedisPool getJedisPool(){
        if (jedisPool!=null){
            return jedisPool;
        }
        return JedisPoolHolder.getJedisPool();
    }


    /**
     * 获取Jedis对象
     * @return
     */
    public static Jedis getJedis(){
        if (jedis==null){
            jedis=getJedisPool().getResource();
        }
        return jedis;
    }

    /**
     * 归还jedis连接
     * @param jedis jedis对象
     * @param hostName host
     * @param port 端口号
     */
    public static void releaseJedis(Jedis jedis,String hostName,int port){
        if (jedis!=null){
            getJedisPool().returnResource(jedis);
        }
    }
}
