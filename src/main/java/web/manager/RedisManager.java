package web.manager;

/**
 * Created by vector01.yao on 2017/6/20.
 */
public interface RedisManager {
    String get(String key);
    void set(String key,String value);
    void set(String key, String value, long keepAliveTime);
    void delete(String key);
    void initJedisPool();
}
