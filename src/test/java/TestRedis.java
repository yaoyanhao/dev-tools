import base.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import web.entity.User;
import web.helper.redis.RedisManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by vector01.yao on 2017/6/28.
 */
public class TestRedis extends BaseTest {

    @Autowired
    private RedisManager redisManager;

    @Test
    public void testRedis() throws InterruptedException {
        User user=new User();
        user.setUserId("123");
        user.setUserName("123423");
        redisManager.setObject("123",user,10, TimeUnit.SECONDS);

        User beforeUser=redisManager.getObject("123",User.class);
        Assert.assertTrue(beforeUser!=null);
        TimeUnit.SECONDS.sleep(10);

        User afterUser=redisManager.getObject("123",User.class);
        Assert.assertTrue(afterUser==null);
    }

}
