package web.helper.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import utils.JedisUtil;

/**
 * Created by vector01.yao on 2017/6/20.
 */
@Component
public class DefaultRedisManager implements StringRedisManager {
    private Logger logger= LoggerFactory.getLogger(DefaultRedisManager.class);

    private JedisPool jedisPool;

    public DefaultRedisManager(){
        initJedisPool();
    }

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

    public void set(String key, String value) {
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            jedis.set(key,value);
        }catch (Exception e){
            logger.error("set jedis value error!key="+key+",value="+value+",exceprion:"+e);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
    }

    public void set(String key, String value, long keepAliveTime) {
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            jedis.set(key,value,"NX","EX",keepAliveTime);
            jedis.set(key,value);
        }catch (Exception e){
            logger.error("set jedis value error!key="+key+",value="+value+",keepAliveTime="+keepAliveTime+" message:"+e);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
    }

    public void delete(String key) {
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            jedis.del(key);
        }catch (Exception e){
            logger.error("delete jedis value error!key="+key+",exception:"+e);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }finally {
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }
    }

    private void initJedisPool() {
        try {
            jedisPool=JedisUtil.getJedisPool();
        }catch (Exception e){
            logger.error("init JedisPool error! message:"+e.getMessage());
        }
    }
}
