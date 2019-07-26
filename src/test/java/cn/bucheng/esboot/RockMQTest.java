package cn.bucheng.esboot;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;

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
        producer.setRetryTimesWhenSendFailed(3);
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("test-topic", "testA", "key" + i, "hello word".getBytes());
            producer.send(message);
        }
        producer.shutdown();

    }

    @Test
    public void testConsumer() throws MQClientException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("test-topic", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //采用集群模式，其消息消费的偏移量会存放到远程的broken上面，自动实现负载均衡
        consumer.setMessageModel(MessageModel.CLUSTERING);
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
        countDownLatch.await();
    }


    @Test
    public void testBroadCasetConsumer() throws MQClientException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-consumer");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("test-topic", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //采用广播模式
        consumer.setMessageModel(MessageModel.BROADCASTING);
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
        countDownLatch.await();
    }



    @Test
    public void testAssignTopicQueue() throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("test-topic-queue");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        MessageQueueSelector selector = new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                return list.get((Integer)arg);
            }
        };

        producer.setRetryTimesWhenSendFailed(3);
        for(int i=0;i<10;i++) {
            Message message = new Message("test-topic", "hello word".getBytes());
            SendResult send = producer.send(message, selector, 1);
            if(send.getSendStatus()== SendStatus.SEND_OK){
                log.info("send ok");
            }
        }

        producer.shutdown();
    }
}
