package cn.bucheng.esboot.binlog.sender.instance;

import cn.bucheng.esboot.binlog.sender.ISender;
import cn.bucheng.esboot.mq.RocketMQProducer;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author ：yinchong
 * @create ：2019/7/25 11:08
 * @description：
 * @modified By：
 * @version:
 */
@Component("rocketMQSender")
@Slf4j
public class RocketMQSender implements ISender {
    @Autowired
    private RocketMQProducer producer;
    @Value("${rocketmq.topic.book.merge.name}")
    private String mergeTopicName;
    @Value("${rocketmq.topic.book.delete.name}")
    private String deleteTopicName;

    @Override
    public void send(Object t) {
        if (t instanceof Long) {
            delete((Long) t);
        } else {
            mergeSend(t);
        }
    }

    private void mergeSend(Object t) {
        String s = JSON.toJSONString(t);
        Message message = new Message(mergeTopicName, s.getBytes());
        try {
            SendResult send = producer.getProducer().send(message);
            if (send == null) {
                log.error("send merge message fail");
                return;
            }
            if (send.getSendStatus() != SendStatus.SEND_OK) {
                log.error("send merge message fail");
                return;
            }
            log.info("send merge message ok");
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("send merge message fail,cause:" + e);
        } catch (RemotingException e) {
            e.printStackTrace();
            log.error("send merge message fail,cause:" + e);
        } catch (MQBrokerException e) {
            e.printStackTrace();
            log.error("send merge message fail,cause:" + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("send merge message fail,cause:" + e);
        }
    }


    public void delete(Serializable id) {
        String s = id + "";
        Message message = new Message(deleteTopicName, s.getBytes());
        try {
            SendResult send = producer.getProducer().send(message);
            if (send == null) {
                log.error("send delete message fail");
                return;
            }
            if (send.getSendStatus() != SendStatus.SEND_OK) {
                log.error("send delete message fail");
                return;
            }
            log.info("send delete message ok");
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("send delete message fail,cause:" + e);
        } catch (RemotingException e) {
            e.printStackTrace();
            log.error("send delete message fail,cause:" + e);
        } catch (MQBrokerException e) {
            e.printStackTrace();
            log.error("send delete message fail,cause:" + e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("send delete message fail,cause:" + e);
        }
    }
}
