package cn.bucheng.esboot.binlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author buchengyin
 * @create 2019/7/24 22:31
 * @describe
 */
@Component
public class BinlogRunner implements CommandLineRunner {
    @Autowired
    private BinLogComponent binLogComponent;

    @Override
    public void run(String... args) throws Exception {
        binLogComponent.init();
    }
}
