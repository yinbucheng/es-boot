package cn.bucheng.esboot.binlog;

import cn.bucheng.esboot.binlog.holder.TableColumnIdAndNameHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author buchengyin
 * @create 2019/7/24 22:31
 * @describe
 */
@Component
@Slf4j
public class BinlogRunner implements CommandLineRunner {
    @Autowired
    private TableColumnIdAndNameHolder holder;

    @Override
    public void run(String... args) throws Exception {
        log.info("===========init schema and table ============");
        holder.init();
    }
}
