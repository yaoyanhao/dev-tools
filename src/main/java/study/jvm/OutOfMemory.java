package study.jvm;

import web.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vector01.yao on 2017/7/18.
 */
public class OutOfMemory {
    public static void main(String[] args) {
        /**
         * 堆内存溢出模拟：不断创建新对象
         */
        List<User> users=new ArrayList<>();
        while (true){
            users.add(new User());
            System.out.println("do");
        }

        /**
         * 虚拟机栈内存溢出模拟
         */
    }
}
