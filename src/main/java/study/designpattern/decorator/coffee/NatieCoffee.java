package study.designpattern.decorator.coffee;

import study.designpattern.decorator.Coffee;

/**
 * Created by vector01.yao on 2017/6/6.
 */
public class NatieCoffee extends Coffee {

    public NatieCoffee(){
        description="拿铁咖啡";
    }

    @Override
    public double cost() {
        return 20;
    }
}
