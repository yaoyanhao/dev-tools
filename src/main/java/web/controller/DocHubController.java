package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.DocService;

/**
 * Created by vector01.yao on 2017/7/15.
 */
@Controller
public class DocHubController {

    @Autowired
    private DocService docService;

    @RequestMapping(value = "docSearch")
    @ResponseBody
    public String docSearch(String keyWord){
        try {
            return docService.getDoc(keyWord);
        } catch (InterruptedException e) {
            return "fail";
        }
    }

}
