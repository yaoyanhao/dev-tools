package web.helper.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by vector01.yao on 2017/6/23.
 */
@Component
public class DefaultSpringRedisManager implements RedisManager {

    private Logger logger= LoggerFactory.getLogger(DefaultSpringRedisManager.class);

    @Autowired
    private RedisTemplate redisTemplate;

    public void setString(String key, String value, long keepAliveTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key,value,keepAliveTime,timeUnit);
    }

    public String getString(String key) {
        return redisTemplate.opsForValue().get(key).toString();
    }

    public <T> void setObject(String key, T value, long keepAliveTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key,value,keepAliveTime,timeUnit);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public <T> void setObjectList(String key, List<T> value, long keepAliveTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key,value,keepAliveTime,timeUnit);
    }

    public <T> List<T> getObjectList(String key, Class<T> clazz) {
        return (List<T>) redisTemplate.opsForValue().get(key);
    }

    public void deleteString(String key) {
        redisTemplate.delete(key);
    }
}
