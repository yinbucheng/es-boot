package cn.bucheng.esboot.binlog.global;


import cn.bucheng.esboot.entity.BinLogPO;
import cn.bucheng.esboot.mapper.BinLogMapper;
import cn.bucheng.mysql.callback.BinLogCommitPosition;
import cn.bucheng.mysql.callback.CommitPositionCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/7/29 11:16
 * @description：
 * @modified By：
 * @version:
 */
@Component
@Slf4j
public class GlobalCommitPositionHandle implements BinLogCommitPosition {
    @Autowired
    private BinLogMapper mapper;


    @Override
    public void commitBinLogPosition(long position) {
        BinLogPO binLogPO = mapper.selectById(1L);
        binLogPO.setPosition(position);
        binLogPO.setUpdateTime(new Date());
        int row = mapper.updateById(binLogPO);
        if (row <= 0) {
            log.error("update binlog position fail,fileName:{} position:{}", binLogPO.getFileName(), binLogPO.getPosition());
        }
    }
}
