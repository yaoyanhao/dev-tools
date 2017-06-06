package study.designpattern.decorator.decorator;

import study.designpattern.decorator.Coffee;

/**
 * Created by vector01.yao on 2017/6/6.
 */
public abstract class AbstractDecorator extends Coffee{
    public abstract String getDescription();
}
