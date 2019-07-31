package cn.bucheng.esboot.binlog.global;

import cn.bucheng.esboot.entity.BinLogPO;
import cn.bucheng.esboot.mapper.BinLogMapper;
import cn.bucheng.mysql.listener.IBinLogFileListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate redisTemplate;

    @Override
    public void handleBinLogFile(String fileName, long position) {
        log.info(" save fileName:{} position:{} to redis", fileName, position);
        redisTemplate.opsForHash().put("es-boot-binLog", "filename", fileName);
        redisTemplate.opsForHash().put("es-boot-binlog", "position", position);
    }
}
