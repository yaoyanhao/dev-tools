package study.designpattern.observer.observer;

/**
 * Created by vector01.yao on 2017/6/3.
 */
public class CurrentBroadcast extends Broadcast {

    /*
        观察者拿到的数据都是被观察者“推”过来的数据。也就是说，被观察者不管谁订阅了自己，都一视同仁，推同样的数据
        当然，也可以采用观察者主动去被观察者主动“拉去”数据，但是这就要求被观察着必须对外开放一些get方法
     */
    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature=temperature;
        this.humidity=humidity;
        this.pressure=pressure;
        display();
    }

    @Override
    public void display() {
        System.out.println("当前温度："+temperature+"摄氏度");
        System.out.println("当前湿度："+humidity);
        System.out.println("当前气压："+pressure+"KP");
    }
}
