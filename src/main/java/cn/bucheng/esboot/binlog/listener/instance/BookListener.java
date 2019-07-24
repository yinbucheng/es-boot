package cn.bucheng.esboot.binlog.listener.instance;

import cn.bucheng.esboot.binlog.BinLogUtils;
import cn.bucheng.esboot.binlog.holder.TableBO;
import cn.bucheng.esboot.binlog.holder.TableColumnIdAndNameHolder;
import cn.bucheng.esboot.binlog.listener.CompositeListener;
import cn.bucheng.esboot.binlog.listener.IListener;
import cn.bucheng.esboot.dao.BookRepository;
import cn.bucheng.esboot.entity.BookEntity;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：yinchong
 * @create ：2019/7/24 19:46
 * @description：
 * @modified By：
 * @version:
 */
@Component
@Slf4j
@Lazy(false)
public class BookListener implements IListener {
    @Autowired
    private TableColumnIdAndNameHolder holder;
    @Autowired
    private CompositeListener listener;
    @Autowired
    private BookRepository bookRepository;

    private final String DBNAME = "ad_test";
    private final String TABLENAME = "ad_book";

    @Override
    public void onEvent(EventData data, int type) {
        if (type == IListener.ADD) {
            WriteRowsEventData addEvent = (WriteRowsEventData) data;
            List<Serializable[]> rows = addEvent.getRows();
            if (rows != null) {
                for (Serializable[] row : rows) {
                    int length = row.length;
                    Map<String, Object> result = new HashMap<>();
                    for (int i = 0; i < length; i++) {
                        TableBO tableBO = holder.getTableBO(DBNAME, TABLENAME);
                        String name = tableBO.getJavaName(i);
                        result.put(name, row[i]);
                    }

                    BookEntity entity = BinLogUtils.decode(BookEntity.class, result);
                    String content = null;
                    try {
                        content = new String((byte[]) result.get("content"), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    entity.setContent(content);
                    bookRepository.save(entity);
                }
            }
        } else if (type == IListener.DELETE) {
            DeleteRowsEventData deleteData = (DeleteRowsEventData) data;
            List<Serializable[]> rows = deleteData.getRows();
            if (rows != null) {
                for (Serializable[] row : rows) {
                    bookRepository.deleteById((Long) row[0]);
                }
            }

        }
    }

    @PostConstruct
    @Override
    public void register() {
        holder.register(DBNAME, TABLENAME, this);
        listener.register(DBNAME, TABLENAME, this);
        holder.init();
    }

    @Override
    public Map<String, String> mappingColumn() {
        Map<String, String> mappings = new HashMap<>();
        mappings.put("id", "id");
        mappings.put("name", "name");
        mappings.put("title", "title");
        mappings.put("content", "content");
        mappings.put("writer", "writer");
        mappings.put("create_time", "createTime");
        mappings.put("update_time", "updateTime");
        return mappings;
    }

}
