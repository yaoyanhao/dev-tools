package study.designpattern.decorator.decorator;

import study.designpattern.decorator.Coffee;

/**
 * Created by vector01.yao on 2017/6/6.
 */
public class CoffeeWithMilk extends AbstractDecorator {
    private Coffee coffee;
    public CoffeeWithMilk(Coffee coffee){
        this.coffee=coffee;
    }
    @Override
    public String getDescription() {
        return coffee.getDescription()+",加奶";
    }

    @Override
    public double cost() {
        return coffee.cost()+1;
    }
}
