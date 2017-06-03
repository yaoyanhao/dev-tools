package study.designpattern.strategy;

import study.designpattern.strategy.behavior.FlyBehavior;

/**
 * Created by vector01.yao on 2017/6/3.
 * [原则]：将变动的部分独立成若干算法族，将不变的部分类的上游提
 */
public abstract class Man {

    //通过组合的方式，将变动的部分引入  [原则]：组合优于继承
    protected FlyBehavior flyBehavior;

    protected abstract void display();

    //共有属性
    public void walk(){
        System.out.println("走两步,没病走两步....");
    }

    public void doFly(){
        flyBehavior.fly();
    }

}
