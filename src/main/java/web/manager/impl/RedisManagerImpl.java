package web.manager.impl;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import utils.JedisUtil;
import web.entity.User;
import web.manager.RedisManager;

/**
 * Created by vector01.yao on 2017/6/20.
 */
@Component
public class RedisManagerImpl implements RedisManager {


//    @Autowired
//    private RedisTemplate<String,User> redisTemplate;

    public String getUser(String key) {
        Jedis jedis= JedisUtil.getJedis("10.199.201.229",6379);
        return jedis.get("test");
    }

    public void addUser(String key, User user) {
        Jedis jedis= JedisUtil.getJedis("10.199.201.229",6379);
        jedis.set("test",user.toString());
    }
}
