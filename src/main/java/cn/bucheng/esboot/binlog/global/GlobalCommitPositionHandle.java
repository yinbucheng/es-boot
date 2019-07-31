package cn.bucheng.esboot.binlog.global;


import cn.bucheng.esboot.entity.BinLogPO;
import cn.bucheng.esboot.mapper.BinLogMapper;
import cn.bucheng.mysql.callback.BinLogCommitPosition;
import cn.bucheng.mysql.callback.CommitPositionCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public void commitBinLogPosition(long position) {
        log.info("update position to redis position:{}", position);
        redisTemplate.opsForHash().put("es-boot-binLog", "position", position + "");
    }
}
