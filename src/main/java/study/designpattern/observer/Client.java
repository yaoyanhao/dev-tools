package study.designpattern.observer;

import study.designpattern.observer.observer.CurrentBroadcast;
import study.designpattern.observer.observer.FutureBroadcast;
import study.designpattern.observer.subject.WeatherData;

/**
 * Created by vector01.yao on 2017/6/3.
 * 测试
 */
public class Client {
    public static void main(String[] args) {
        WeatherData weatherData=new WeatherData();//主题：被订阅者
        CurrentBroadcast currentBroadcast=new CurrentBroadcast();//订阅者1
        FutureBroadcast futureBroadcast=new FutureBroadcast();//订阅者2
        currentBroadcast.register(weatherData);
        futureBroadcast.register(weatherData);
        weatherData.updateWeatherData(30,50,100);

        //将订阅者2取消订阅
        System.out.println("订阅者2取消预订后...........");
        futureBroadcast.unRegister(weatherData);
        weatherData.updateWeatherData(40,60,110);
    }
}
