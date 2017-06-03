package study.designpattern.observer.observer;

/**
 * Created by vector01.yao on 2017/6/3.
 */
public class FutureBroadcast extends Broadcast {

    @Override
    public void update(float temperature, float humidity, float pressure) {
        temperature=temperature+1;
        humidity=humidity+1;
        pressure=pressure+1;
        display();
    }

    @Override
    public void display() {
        System.out.println("明日温度："+temperature+"摄氏度");
        System.out.println("明日湿度："+humidity);
        System.out.println("明日气压："+pressure+"KP");
    }
}
