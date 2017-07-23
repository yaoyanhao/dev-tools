package study.jvm.oomTest;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by vector01.yao on 2017/7/22.
 * 被测试采用java1.7测试，由于java1.7把运行时常量池从方法区移除，因此不再测试常量池的OOM
 * 方法区用于存放Class的相关信息：如类名、访问修饰符等。
 * 测试思路为：创建大量的类填满方法区。
 * 设置方法区大小为10M：-XX:PermSize=2M -XX:MaxPermSize=2M
 */
public class MethodAreaOOMTest {
    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
        }
    }

    static class OOMObject {

    }
}
