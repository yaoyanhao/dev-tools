package web.helper.redis;

/**
 * Created by vector01.yao on 2017/6/20.
 */
public interface StringRedisManager {
    String get(String key);
    void set(String key,String value);
    void set(String key, String value, long keepAliveTime);
    void delete(String key);
}
