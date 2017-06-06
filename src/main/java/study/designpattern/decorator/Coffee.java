package study.designpattern.decorator;

/**
 * Created by vector01.yao on 2017/6/5.
 */
public abstract class Coffee {
    protected String description="unknown";

    public String getDescription(){
        return description;
    }
    public abstract double cost();
}
