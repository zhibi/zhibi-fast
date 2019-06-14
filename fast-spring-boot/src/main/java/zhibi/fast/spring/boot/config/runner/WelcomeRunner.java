package zhibi.fast.spring.boot.config.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author 执笔
 * @date 2019/5/10 12:39
 */
@Component
public class WelcomeRunner implements CommandLineRunner {

    /**
     * 项目名称
     */
    @Value("${spring.application.name:}")
    private String applicationName;

    /**
     * 端口号
     */
    @Value("${server.port:8080}")
    private String port;
    /**
     * 项目路径
     */
    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Override
    public void run(String... args) throws Exception {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(" 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓\n");
        System.out.println("       项目启动 :: " + applicationName);
        System.out.println("       项目地址 :: http://" + localHost.getHostAddress() + ":" + port + "/" + contextPath);
        System.out.println("\n 〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓〓");
    }
}
