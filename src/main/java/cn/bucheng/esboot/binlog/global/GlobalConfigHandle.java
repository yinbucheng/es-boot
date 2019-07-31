package cn.bucheng.esboot.binlog.global;

import cn.bucheng.esboot.entity.BinLogPO;
import cn.bucheng.esboot.mapper.BinLogMapper;
import cn.bucheng.mysql.binlog.BinLogConfig;
import cn.bucheng.mysql.callback.BinlogConfigCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：yinchong
 * @create ：2019/7/29 11:20
 * @description：
 * @modified By：
 * @version:
 */
@Slf4j
@Component
public class GlobalConfigHandle implements BinlogConfigCallback {
    @Autowired
    private BinLogMapper mapper;


    @Override
    public void configCallback(BinLogConfig config) {
        BinLogPO binLogPO = mapper.selectById(1L);
        String fileName = binLogPO.getFileName();
        Long position = binLogPO.getPosition();
        if (!Strings.isBlank(fileName)) {
            log.info("begin load file:{} ,position:{}", fileName, position);
            config.setFile(fileName);
            config.setPosition(position);
        }
    }
}
