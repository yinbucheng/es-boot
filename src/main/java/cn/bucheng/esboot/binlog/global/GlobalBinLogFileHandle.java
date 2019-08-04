package cn.bucheng.esboot.binlog.global;

import cn.bucheng.mysql.constant.BinLogConstant;
import cn.bucheng.mysql.listener.IBinLogFileListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void handleBinLogFile(String fileName, long position) {
        redisTemplate.opsForHash().put(BinLogConstant.BINLOG_PREFIX + "es", BinLogConstant.BINLOG_FILE, fileName);
        redisTemplate.opsForHash().put(BinLogConstant.BINLOG_PREFIX + "es", BinLogConstant.BINLOG_POSITION, position + "");
        log.info("save binLogFile:{} position:{}", fileName, position);
    }
}
