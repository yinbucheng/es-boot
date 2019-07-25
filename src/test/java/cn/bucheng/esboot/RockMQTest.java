package cn.bucheng.esboot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.util.List;

/**
 * @author buchengyin
 * @create 2019/7/25 20:30
 * @describe
 */
@Slf4j
public class RockMQTest {

    @Test
    public void startProducer() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("test-producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setRetryTimesWhenSendAsyncFailed(3);
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("test-topic", "testA", "key" + i, "hello word".getBytes());
            producer.send(message);
        }

    }

    @Test
    public void testConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("test-topic", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt msg = list.get(0);
                try {
                    String key = msg.getKeys();
                    String msgId = msg.getMsgId();
                    String tag = msg.getTags();
                    String content = new String(msg.getBody());
                    if (key.equals("key1")) {
                        int i = 1 / 0;
                    }
                    log.info(key + " " + msgId + " " + tag + " " + content);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    int time = msg.getReconsumeTimes();
                    if (time == 3) {
                        log.info("write log to file......." + msg.getMsgId());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    log.error(e.toString());
                    log.info(msg.getKeys() + " retry time:" + msg.getReconsumeTimes());
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });

        consumer.start();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
