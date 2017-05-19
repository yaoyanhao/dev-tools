package base;

import java.lang.annotation.*;

/**
 * Created by vector01.yao on 2017/5/18.
 * 单元测试前置和后置处理注解
 */
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrePostSql {
    /**
     * 方法或类前执行SQL文件地址，默认""
     */
    String preSql() default "";

    /**
     * 方法或类后执行SQL文件地址,默认""
     */
    String postSql() default "";
}
