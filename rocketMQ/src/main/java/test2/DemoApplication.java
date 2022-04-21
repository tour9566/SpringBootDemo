package test2;

/* @ProjectName:    demo
 * @Package:        test2
 * @ClassName:      DemoApplication
 * @author:     jiangtao
 * @description:
 * @date:    2022/4/19 16:06
 * @version:    1.0
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
