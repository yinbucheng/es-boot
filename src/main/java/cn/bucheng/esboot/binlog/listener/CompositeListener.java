package cn.bucheng.esboot.binlog.listener;

import cn.bucheng.esboot.binlog.BinLogUtils;
import cn.bucheng.esboot.binlog.holder.TableColumnIdAndNameHolder;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：yinchong
 * @create ：2019/7/24 19:01
 * @description：组合监听器，会将事件转发到感兴趣的监听器上面去
 * @modified By：
 * @version:
 */
@Component
@Slf4j
public class CompositeListener implements BinaryLogClient.EventListener {
    String dbName;
    String tableName;


    private ConcurrentHashMap<String, IListener> listeners = new ConcurrentHashMap<>();

    public void register(String dbName, String tableName, IListener listener) {
        listeners.put(BinLogUtils.createKey(dbName, tableName), listener);
    }


    @Override
    public void onEvent(Event event) {
        EventType eventType = event.getHeader().getEventType();
        if (eventType == EventType.TABLE_MAP) {
            TableMapEventData mapData = (TableMapEventData) event.getData();
            dbName = mapData.getDatabase().toLowerCase();
            tableName = mapData.getTable().toLowerCase();
        }

        if (eventType == EventType.EXT_WRITE_ROWS) {
            listeners.get(BinLogUtils.createKey(dbName, tableName)).onEvent(event.getData(), IListener.ADD);
        } else if (eventType == EventType.EXT_DELETE_ROWS) {
            listeners.get(BinLogUtils.createKey(dbName, tableName)).onEvent(event.getData(), IListener.DELETE);
        } else if (eventType == EventType.EXT_UPDATE_ROWS) {
            listeners.get(BinLogUtils.createKey(dbName, tableName)).onEvent(event.getData(), IListener.UPDATE);
        }
    }
}
