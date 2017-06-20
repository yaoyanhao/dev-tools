package web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import web.entity.User;
import web.manager.RedisManager;

/**
 * Created by vector01.yao on 2017/6/20.
 */
@RestController
public class RedisController {
    private Logger logger= LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisManager redisManager;

    @RequestMapping(value = "testRedis")
    @ResponseBody
    public String testRedis(){
        User user=new User();
        user.setUserId("123");
        user.setUserName("123423");
        redisManager.addUser("user1",user);
        logger.error("写入redis成功！");
        return redisManager.getUser("user1");
    }
}
