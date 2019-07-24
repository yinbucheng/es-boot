package cn.bucheng.esboot.binlog.listener;

import cn.bucheng.esboot.binlog.holder.TableColumnIdAndNameHolder;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author ：yinchong
 * @create ：2019/7/24 19:06
 * @description：
 * @modified By：
 * @version:
 */
public interface IListener {
    public static final int ADD = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    //处理事件
    void onEvent(EventData data, int type);
    //注册监听器
    @PostConstruct
    void register();
    //返回数据库字段和java字段对应关系
    Map<String, String> mappingColumn();
}
