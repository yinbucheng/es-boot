package cn.bucheng.esboot.binlog.global;

import cn.bucheng.mysql.binlog.BinLogConfig;
import cn.bucheng.mysql.callback.BinLogConfigHook;
import lombok.extern.slf4j.Slf4j;
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
        if (filename != null && !filename.equals("") && position != null && !"".equals(position + "")) {
            log.info("begin load filename:{}, position:{}", filename, position);
            config.setFile(filename + "");
            config.setPosition(Long.parseLong(position + ""));
        }
    }
}
