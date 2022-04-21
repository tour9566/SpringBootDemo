package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import test.worker.SpringBootProducer;

/* @ProjectName:    demo
 * @Package:        test
 * @ClassName:      App
 * @author:     jiangtao
 * @description:
 * @date:    2022/4/20 9:01
 * @version:    1.0
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
