package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by vector01.yao on 2017/7/15.
 */
@Controller
public class DocHubController {

    @RequestMapping(value = "testRedis")
    @ResponseBody
    public String docSearch(){
        return null;
    }

}
