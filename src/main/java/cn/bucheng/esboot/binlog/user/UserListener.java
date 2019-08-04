package cn.bucheng.esboot.binlog.user;

import cn.bucheng.mysql.listener.IListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author ：yinchong
 * @create ：2019/8/1 9:35
 * @description：
 * @modified By：
 * @version:
 */
@Component
@Slf4j
public class UserListener implements IListener<UserPO> {
    @Override
    public Class getClassType() {
        return UserPO.class;
    }

    @Override
    public void saveEvent(UserPO data) {
        log.info("save user event1,content:{}", data);
    }

    @Override
    public void updateEvent(UserPO data) {
        log.info("update user event1,content:{}", data);
    }

    @Override
    public void deleteEvent(Serializable id) {
        log.info("delete user event1,id:{}", id);
    }
}
