package study.designpattern.decorator;

import study.designpattern.decorator.coffee.MoKaCoffee;
import study.designpattern.decorator.decorator.CoffeeWithMilk;

/**
 * Created by vector01.yao on 2017/6/6.
 */
public class Client {
    public static void main(String[] args) {
        Coffee coffee=new MoKaCoffee();
        coffee=new CoffeeWithMilk(coffee);
        System.out.println(coffee.getDescription()+":"+coffee.cost());
    }
}
