package base;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import utils.DataBaseHelper;

/**
 * Created by vector01.yao on 2017/5/18.
 * bill系统单元测试执行监听，处理类or方法的前置操作和后置操作
 * [注]：不创建数据库，直接使用db.properties文件配置的数据库作为testcase的数据库
 */
public class CustomTestExecutionListener extends AbstractTestExecutionListener {

    private Logger logger= LoggerFactory.getLogger(CustomTestExecutionListener.class);

    /**
     * 数据库信息：配置测试基础数据库
     */
    private static final String DB_PROPERTY_FILE="db.properties";

    /**
     * 对象锁  for同步
     */
    private static final Object lock = new Object();

    public CustomTestExecutionListener() {
        DataBaseHelper.initDB(DB_PROPERTY_FILE);//加载db.properties文件配置的参数
    }

    /**
     * 单元测试类前置操作
     * @param testContext 单元测试上下文
     * @throws Exception
     */
    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        try {
            logger.info("beforeTestClass called start!");
            Class<?> testClass = testContext.getTestClass();
            //检查是否需要预执行SQL
            PrePostSql prePostSql = testClass.getAnnotation(PrePostSql.class);
            if (prePostSql != null) {
                String preSqlFile = prePostSql.preSql();//preSQL文件地址
                executePrePostSql(preSqlFile);
            }
            logger.info("beforeTestClass called with [" + testContext + "].");
        } catch (RuntimeException e) {
            logger.error("beforeTestClass called exception:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试方法前置操作
     * @param testContext 单元测试上下文
     * @throws Exception
     */
    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        try {
            logger.info("beforeTestMethod called start!");
            Class<?> testClass = testContext.getTestClass();
            //检查是否需要预执行SQL
            PrePostSql prePostSql = testClass.getAnnotation(PrePostSql.class);
            if (prePostSql != null) {
                String preSqlFile = prePostSql.preSql();//preSQL文件地址
                executePrePostSql(preSqlFile);
            }
            logger.info("beforeTestMethod called with [" + testContext + "].");
        } catch (RuntimeException e) {
            logger.error("beforeTestMethod called exception:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试方法后置操作
     * @param testContext 单元测试上下文
     * @throws Exception
     */
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        try {
            logger.info("afterTestMethod called start!");
            Class<?> testClass = testContext.getTestClass();
            //检查是否需要预执行SQL
            PrePostSql prePostSql = testClass.getAnnotation(PrePostSql.class);
            if (prePostSql != null) {
                String postSqlFile = prePostSql.postSql();//postSQL文件地址
                executePrePostSql(postSqlFile);
            }
            logger.info("afterTestMethod called with [" + testContext + "].");
        } catch (RuntimeException e) {
            logger.error("afterTestMethod called exception:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试类后置操作
     * @param testContext 单元测试上下文
     * @throws Exception
     */
    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        try {
            logger.info("afterTestClass called start!");
            Class<?> testClass = testContext.getTestClass();
            //检查是否需要预执行SQL
            PrePostSql prePostSql = testClass.getAnnotation(PrePostSql.class);
            if (prePostSql != null) {
                String postSqlFile = prePostSql.postSql();//postSQL文件地址
                executePrePostSql(postSqlFile);
            }
            logger.info("afterTestClass called with [" + testContext + "].");
        } catch (RuntimeException e) {
            logger.error("afterTestClass called exception:" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行前置、后置SQL
     * @param sqlLocation sql文件位置
     */
    private void executePrePostSql(String sqlLocation){
        if (StringUtils.isNotBlank(sqlLocation)) {
            sqlLocation = sqlLocation.replace("classpath:", "");
            sqlLocation = sqlLocation.replace("classpath*:", "");
            synchronized (lock) {
                DataBaseHelper.executeSQL(sqlLocation);
            }
        }
    }
}
