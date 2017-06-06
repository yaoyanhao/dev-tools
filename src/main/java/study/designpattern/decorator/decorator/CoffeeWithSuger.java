package study.designpattern.decorator.decorator;

import study.designpattern.decorator.Coffee;

/**
 * Created by vector01.yao on 2017/6/6.
 */
public class CoffeeWithSuger extends AbstractDecorator {
    private Coffee coffee;

    CoffeeWithSuger(Coffee coffee){
        this.coffee=coffee;
    }
    @Override
    public String getDescription() {
        return coffee.getDescription()+",加糖";
    }

    @Override
    public double cost() {
        return coffee.cost()+0.5;
    }
}
