package test.worker;

/* @ProjectName:    demo
 * @Package:        test.worker
 * @ClassName:      SpringBootConsumer
 * @author:     jiangtao
 * @description:
 * @date:    2022/4/20 9:11
 * @version:    1.0
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Component
@RocketMQMessageListener(topic = "topic_springboot",consumerGroup = "group1")
@Slf4j
public class SpringBootConsumer implements RocketMQListener {
    @Override
    public void onMessage(Object message) {
        log.info("Received message :" + message);
    }
}
