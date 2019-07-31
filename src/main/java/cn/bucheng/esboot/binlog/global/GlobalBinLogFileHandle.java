package cn.bucheng.esboot.binlog.global;

import cn.bucheng.esboot.entity.BinLogPO;
import cn.bucheng.esboot.mapper.BinLogMapper;
import cn.bucheng.mysql.listener.IBinLogFileListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/7/31 15:40
 * @description：
 * @modified By：
 * @version:
 */
@Component
@Slf4j
public class GlobalBinLogFileHandle implements IBinLogFileListener {
    @Autowired
    private BinLogMapper mapper;

    @Override
    public void handleBinLogFile(String fileName) {
        BinLogPO binLogPO = mapper.selectById(1L);
        binLogPO.setFileName(fileName);
        binLogPO.setPosition(0L);
        binLogPO.setUpdateTime(new Date());
        int row = mapper.updateById(binLogPO);
        if (row <= 0) {
            log.error("update binlog record fail,fileName:{}", fileName);
        }
    }
}
