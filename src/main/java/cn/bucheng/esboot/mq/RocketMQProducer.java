package cn.bucheng.esboot.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ：yinchong
 * @create ：2019/7/25 10:51
 * @description：
 * @modified By：
 * @version:
 */
@Component
@Slf4j
public class RocketMQProducer {
    @Value("${rocketmq.nameserver.address}")
    private String nameserverAddr;

    private DefaultMQProducer producer;

    @PostConstruct
    public void init() {
        producer = new DefaultMQProducer("default-rocketmq-producer");
        producer.setRetryTimesWhenSendAsyncFailed(3);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    public DefaultMQProducer getProducer() {
        return producer;
    }
}
