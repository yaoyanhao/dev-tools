package study.aop;

/**
 * Created by vector01.yao on 2017/8/2.
 */
public class GreetingProxy implements Greeting{
    private  Greeting greeting;

    public GreetingProxy(Greeting greeting){
        this.greeting=greeting;
    }

    @Override
    public void sayHello(String name) {
        before();
        System.out.println("hello!"+name);
        after();
    }

    private void before(){
        System.out.println("before");
    }

    private void after(){
        System.out.println("after");
    }

    public static void main(String[] args) {
        Greeting greeting=new GreetingImpl();
        greeting.sayHello("tom");
    }
}
