package study.designpattern.observer.observer;

import study.designpattern.observer.subject.Subject;

/**
 * Created by vector01.yao on 2017/6/3.
 */
public abstract class Broadcast {
    protected float temperature;
    protected float humidity;
    protected float pressure;

    /**
     * 订阅者主动订阅和取消订阅
     * 订阅者提供订阅方法和取消订阅方法，内部实现调用subject的订阅方法和取消订阅方法
     */
    //订阅
    public void register(Subject subject) {
        subject.registerObserver(this);
    }

    //取消订阅
    public void unRegister(Subject subject) {
        subject.removeObserver(this);
    }

    public abstract void update(float temperature,float humidity,float pressure);
    abstract void display();
}
