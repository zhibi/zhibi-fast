package zhibi.fast.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 执笔
 * @date 2019/4/17 14:12
 */
@SpringBootApplication(scanBasePackages = "zhibi.fast")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
