package cn.bucheng.esboot.binlog;

import cn.bucheng.esboot.binlog.listener.CompositeListener;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * @author ：yinchong
 * @create ：2019/7/24 18:56
 * @description：
 * @modified By：
 * @version:
 */
@Component
@Slf4j
@Lazy(false)
public class BinLogComponent {

    private BinaryLogClient client;
    @Autowired
    private BinLogConfig config;
    @Autowired
    private CompositeListener listener;


    @PostConstruct
    public void init() {
        log.info("==========start binlog client==========");
        Thread thread = new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinlogFile()) &&
                    !config.getBinlogFilePosition().equals(-1L)) {
                log.info("---------set binlog file and position-----------");
                client.setBinlogFilename(config.getBinlogFile());
                client.setBinlogPosition(config.getBinlogFilePosition());
            }

            client.registerEventListener(listener);

            try {
                log.info("connecting to mysql start");
                client.connect();
                log.info("connecting to mysql done");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        thread.setName("binlog-listener-thread");
        thread.start();
    }


    @PreDestroy
    public void close() {
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
