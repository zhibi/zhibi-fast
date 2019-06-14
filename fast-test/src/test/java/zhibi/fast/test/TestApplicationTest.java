package zhibi.fast.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import zhibi.fast.commons.uid.UidGenerator;

/**
 * @author 执笔
 * @date 2019/4/17 14:13
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestApplicationTest {

    @Autowired
    private UidGenerator uidGenerator;

    @Test
    public void test(){
        System.out.println(uidGenerator.getUID());
    }
}