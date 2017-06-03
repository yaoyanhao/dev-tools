package study.designpattern.strategy.behavior;

/**
 * Created by vector01.yao on 2017/6/3.
 */
public class NonFly implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("I can't fly.......");
    }
}
