package study.designpattern.strategy;

import study.designpattern.strategy.behavior.*;

/**
 * Created by vector01.yao on 2017/6/3.
 */
public class Client {
    public static void main(String[] args) {
        //超人
        FlyBehavior flyBehavior=new FlyWithWing();
        Man superMan=new SuperMan(flyBehavior);
        superMan.display();
        superMan.doFly();

        //普通人
        flyBehavior=new NonFly();
        Man commonMan=new CommonPerson(flyBehavior);
        commonMan.display();
        commonMan.doFly();
    }
}
