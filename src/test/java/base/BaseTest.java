package base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;

/**
 * Created by vector01.yao on 2017/5/18.
 * 用于bill系统单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml" }, inheritLocations = true)
@TestExecutionListeners({
        ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        //TransactionalTestExecutionListener.class,
        CustomTestExecutionListener.class })
//@Transactional(value = "transactionManager")//必须和TransactionalTestExecutionListener.class配合使用，测试完成事务回滚，保证数据库干净
public class BaseTest {
}
