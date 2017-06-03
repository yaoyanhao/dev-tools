package study.designpattern.observer.subject;

import study.designpattern.observer.observer.Broadcast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vector01.yao on 2017/6/3.
 * 天气数据对象，包括三个指标：温度，湿度，气压，
 */
public class WeatherData implements Subject{
    private float temperature;
    private float humidity;
    private float pressure;

    //所有观察者集合
    private List<Broadcast> broadcastList=new ArrayList<Broadcast>();

    //添加或删除观察者
    public void registerObserver(Broadcast broadcast){
        broadcastList.add(broadcast);
    }

    public void removeObserver(Broadcast broadcast){
        broadcastList.remove(broadcast);
    }

    //通知所有观察者，向各个观察者推送数据
    public void notifyObservers(){
        for (Broadcast broadcast:broadcastList){
            broadcast.update(temperature,humidity,pressure);
        }
    }

    //更新天气数据，并通知观察者
    public void updateWeatherData(float temperature,float humidity,float pressure){
        this.temperature=temperature;
        this.humidity=humidity;
        this.pressure=pressure;
        notifyObservers();
    }
}
