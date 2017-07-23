package study.jvm.GC;

/**
 * Created by vector01.yao on 2017/7/22.
 * 模拟引用计数法的缺陷：无法回收相互引用的无用对象
 */
public class ReferCountLeakTest {
    public Object instance = null;
    private static final int _1MB = 1024*1024;
    private byte[] bigSize = new byte[2 * _1MB];
    public static void testGC(){
        ReferCountLeakTest obja =new ReferCountLeakTest();
        ReferCountLeakTest objb = new ReferCountLeakTest();

        obja.instance =objb;
        objb.instance =obja;

        obja = null;
        objb = null;

        System.gc();

    }

    public static void main(String[] args) {
        testGC();
    }
}
