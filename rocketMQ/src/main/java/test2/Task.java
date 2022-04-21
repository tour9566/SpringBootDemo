package test2;

/* @ProjectName:    demo
 * @Package:        test2
 * @ClassName:      Task
 * @author:     jiangtao
 * @description:
 * @date:    2022/4/19 16:05
 * @version:    1.0
 */

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class Task {
    @Resource
    private DefaultMQProducer producer;

    /**
     * 每10秒执行一次
     */
    @Scheduled(cron = "0/10 * *  * * ?")
    private void sendMsgToMq() {
        String str = "发送测试消息";
        Message msg;
        try {
            msg = new Message("test-demo"
                    , "111"
                    , UUID.randomUUID().toString()
                    , str.getBytes("utf-8"));
            SendResult result = producer.send(msg);
            if (result.getSendStatus() == SendStatus.SEND_OK) {
                System.out.println("消息发送成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
