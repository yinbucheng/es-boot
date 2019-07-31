package cn.bucheng.esboot.binlog.global;

import cn.bucheng.esboot.entity.BinLogPO;
import cn.bucheng.esboot.mapper.BinLogMapper;
import cn.bucheng.mysql.binlog.BinLogConfig;
import cn.bucheng.mysql.callback.BinLogConfigHook;
import cn.bucheng.mysql.callback.BinlogConfigCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
public class GlobalConfigHandle implements BinLogConfigHook {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public void configReset(BinLogConfig config) {
        Object filename = redisTemplate.opsForHash().get("es-boot-binLog", "filename");
        Object position = redisTemplate.opsForHash().get("es-boot-binLog", "position");
        if (filename != null && !filename.equals("")) {
            log.info("begin load filename:{}, position:{}", filename, position);
            config.setFile(filename + "");
            if (position != null && !"".equals(position + "")) {
                config.setPosition(Long.parseLong(position + ""));
            } else {
                config.setPosition(0L);
            }
        }
    }
}
