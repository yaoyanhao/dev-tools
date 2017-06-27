package web.manager;

/**
 * Created by vector01.yao on 2017/6/23.
 */
public interface SpringRedisManager {
    String get(String key);
    void set(String key,String value);
    void set(String key, String value, long keepAliveTime);
    void delete(String key);
}
