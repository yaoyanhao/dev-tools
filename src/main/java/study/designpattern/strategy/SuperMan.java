package study.designpattern.strategy;

import study.designpattern.strategy.behavior.FlyBehavior;

/**
 * Created by vector01.yao on 2017/6/3.
 */
public class SuperMan extends Man {

    //构造方法传入FlyBehavior和QuackBehavior对象，当然，也可以采用getset方式等
    public SuperMan(FlyBehavior flyBehavior){
        this.flyBehavior=flyBehavior;
    }
    @Override
    protected void display() {
        System.out.println("我，内裤外穿的男人....");
    }
}
