package test.worker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringBootProducerTest {
    @Autowired
    SpringBootProducer springBootProducer;

    @Test
    void sendMessage() {
        for (int i = 0; i < 1000; i++) {
            springBootProducer.sendMessage("topic_springboot",i+" ");
        }
    }
}