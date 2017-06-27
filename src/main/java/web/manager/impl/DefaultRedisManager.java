package web.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import utils.JedisUtil;
import web.manager.RedisManager;

/**
 * Created by vector01.yao on 2017/6/20.
 */
@Component
public class DefaultRedisManager implements RedisManager {
    private Logger logger= LoggerFactory.getLogger(DefaultRedisManager.class);

//    /** redis Host  */
//    private String redisHost="localhost";
//    /** redis端口  */
//    private int redisPort=6379;
//    /** 最大空闲连接数：空闲连接超过这个数，将回收 */
//    private int maxIdle=300;
//    /** 最小空闲连接数：空闲连接小于这个数，将创建新的连接 */
//    private int minIdle=5;
//    /** 最大连接数：能够同时建立的最大连接个数 */
//    private int maxActive=600;
//    /** 最大等待时间（毫秒）获取连接时间超过这个时间，将抛错 */
//    private int maxWait=1000;
//    /** //使用连接时，检测连接是否成功 */
//    private boolean testOnBorrow=true;
//    /**返回连接时，检测连接是否成功*/
//    private boolean testOnReturn=true;

    private JedisPool jedisPool;

    public DefaultRedisManager(){
        initJedisPool();
    }

    @Override
    public String get(String key) {
        String redisStr=null;
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            redisStr=jedis.get(key);
        }catch (Exception e){
            logger.error("get value error! key={}",key);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
        return redisStr;
    }

    @Override
    public void set(String key, String value) {
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            jedis.set(key,value);
        }catch (Exception e){
            logger.error("set redis value error!key="+key+",value="+value+",exceprion:"+e);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
    }

    @Override
    public void set(String key, String value, long keepAliveTime) {
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            jedis.set(key,value,"NX","EX",keepAliveTime);
            jedis.set(key,value);
        }catch (Exception e){
            logger.error("set redis value error!key="+key+",value="+value+",keepAliveTime="+keepAliveTime+" message:"+e);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
    }

    @Override
    public void delete(String key) {
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            jedis.del(key);
        }catch (Exception e){
            logger.error("delete redis value error!key="+key+",exception:"+e);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
    }

    @Override
    public void initJedisPool() {
        try {
            jedisPool=JedisUtil.getJedisPool();
        }catch (Exception e){
            logger.error("init JedisPool error! message:"+e.getMessage());
        }
    }
}
