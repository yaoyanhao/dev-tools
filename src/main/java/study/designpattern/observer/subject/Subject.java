package study.designpattern.observer.subject;

import study.designpattern.observer.observer.Broadcast;

/**
 * Created by vector01.yao on 2017/6/3.
 * 主题接口
 */
public interface Subject {
    void registerObserver(Broadcast broadcast);
    void removeObserver(Broadcast broadcast);
    void notifyObservers();
}
