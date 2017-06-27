package web.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import web.manager.SpringRedisManager;

/**
 * Created by vector01.yao on 2017/6/23.
 */
@Component
public class DefaultSpringRedisManager<T> implements SpringRedisManager {
    private Logger logger= LoggerFactory.getLogger(DefaultSpringRedisManager.class);

    @Autowired
    private RedisTemplate<String,T> redisTemplate;

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public void set(String key, String value, long keepAliveTime) {

    }

    @Override
    public void delete(String key) {

    }
}
