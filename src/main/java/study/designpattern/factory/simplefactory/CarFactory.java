package study.designpattern.factory.simplefactory;

import study.designpattern.factory.BMW;
import study.designpattern.factory.Benz;
import study.designpattern.factory.Jeep;
import study.designpattern.factory.Product;

/**
 * Created by vector01.yao on 2017/6/6.
 * 简单工厂模式，只是简单地将生产产品的工厂独立出来
 */
public class CarFactory {
    public Product createCar(String type){
        if ("BMW".equals(type)){
            return new BMW();
        }else if ("Benz".equals(type)){
            return new Benz();
        }else if ("Jeep".equals(type)){
            return new Jeep();
        }
        return null;
    }
}
