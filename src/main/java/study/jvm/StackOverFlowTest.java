package study.jvm;

/**
 * Created by vector01.yao on 2017/7/22.
 * 通过递归方法模拟StackOverFlowError
 * 单个Stack的容量设置为128K，即-Xss128k
 */
public class StackOverFlowTest {

    private static int stackLength=0;

    public static void main(String[] args) {
        StackOverFlowTest stackOverFlowTest=new StackOverFlowTest();
        try {
            stackOverFlowTest.digui();
        }catch (Throwable e){
            System.out.println("stack length:"+stackLength);
            e.printStackTrace();
        }
    }

    /**
     * 递归方法
     */
    private void digui(){
        stackLength++;
        digui();
    }
}
