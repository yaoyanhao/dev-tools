package web.manager;

import web.entity.User;

/**
 * Created by vector01.yao on 2017/6/20.
 */
public interface RedisManager {
    String getUser(String key);
    void addUser(String key,User user);
}
