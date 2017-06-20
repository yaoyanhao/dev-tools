package web.entity;

import java.io.Serializable;

/**
 * Created by vector01.yao on 2017/6/20.
 */
public class User implements Serializable{
    private String userId;
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toString(){
        return userId+":"+userName;
    }
}
