package cn.bucheng.esboot.mq;

import cn.bucheng.esboot.dao.BookRepository;
import cn.bucheng.esboot.entity.BookEntity;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author ：yinchong
 * @create ：2019/7/25 10:51
 * @description：
 * @modified By：
 * @version:
 */
@Component
@Slf4j
public class RocketMQBookDelConsumer {
    @Value("${rocketmq.nameserver.address}")
    private String nameAddr;
    @Value("${rocketmq.topic.book.delete.name}")
    private String topicName;
    @Autowired
    private BookRepository bookRepository;

    private DefaultMQPushConsumer consumer;

    @SuppressWarnings("all")
    @PostConstruct
    public void init() {
        consumer = new DefaultMQPushConsumer("binlog-es-delete-consumer");
        consumer.setNamesrvAddr(nameAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setConsumeMessageBatchMaxSize(1);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    MessageExt message = list.get(0);
                    String content = new String(message.getBody());
                    Long id = Long.parseLong(content);
                    log.info("accept delete message id:" + id);
                    bookRepository.deleteById(id);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                } catch (Exception e) {
                    log.error(e.toString());
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        });
        try {
            consumer.subscribe(topicName, "*");
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error(e.toString());
            throw new RuntimeException(e);
        }


    }
}
