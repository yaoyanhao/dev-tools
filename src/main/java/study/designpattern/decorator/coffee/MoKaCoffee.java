package study.designpattern.decorator.coffee;

import study.designpattern.decorator.Coffee;

/**
 * Created by vector01.yao on 2017/6/6.
 */
public class MoKaCoffee extends Coffee {

    public MoKaCoffee(){
        description="摩卡咖啡";
    }

    @Override
    public double cost() {
        return 30;
    }
}
