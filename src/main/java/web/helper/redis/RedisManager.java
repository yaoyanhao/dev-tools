package web.helper.redis;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by vector01.yao on 2017/6/27.
 */
public interface RedisManager {

    void setString(String key , String value, long keepAliveTime, TimeUnit timeUnit);

    String getString(String key);

    <T> void setObject(String key , T value ,long keepAliveTime , TimeUnit timeUnit);

    <T> T getObject(String key , Class<T> clazz);

    <T> void setObjectList(String key , List<T> value , long keepAliveTime , TimeUnit timeUnit);

    <T> List<T> getObjectList(String key , Class<T> clazz);

    void deleteString(String key);

}
