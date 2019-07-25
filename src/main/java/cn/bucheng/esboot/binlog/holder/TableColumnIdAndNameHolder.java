package cn.bucheng.esboot.binlog.holder;

import cn.bucheng.esboot.binlog.BinLogUtils;
import cn.bucheng.esboot.binlog.dto.TableBO;
import cn.bucheng.esboot.binlog.listener.IListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：yinchong
 * @create ：2019/7/24 19:13
 * @description：
 * @modified By：
 * @version:
 */
@Component
@Slf4j
public class TableColumnIdAndNameHolder {

    private Map<String, IListener> listeners = new HashMap<>(40);

    private Map<String, TableBO> cache = new HashMap<>(40);

    public static final String SQL = "select table_schema, table_name, column_name, ordinal_position from information_schema.columns";

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public void register(String dbName, String tableName, IListener listener) {
        listeners.put(BinLogUtils.createKey(dbName, tableName), listener);
    }


    public void init() {
            log.info("==============begin load column position and name============");
            //完成id到名称初始化
            jdbcTemplate.query(SQL, new RowMapper<Object>() {
                @Override
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    int position = rs.getInt("ordinal_position");
                    String tableName = rs.getString("table_name").toLowerCase();
                    String dbName = rs.getString("table_schema").toLowerCase();
                    String columnName = rs.getString("column_name");
                    String key = BinLogUtils.createKey(dbName, tableName);
                    TableBO tableBO = cache.get(key);
                    if (tableBO == null) {
                        tableBO = new TableBO();
                        tableBO.setDbName(dbName);
                        tableBO.setTableName(tableName);
                        cache.put(key,tableBO);
                    }
                    tableBO.addColumnIdName(position - 1, columnName);
                    return null;
                }
            });
            //完成mysql列名到java的映射
            for (Map.Entry<String, IListener> entry : listeners.entrySet()) {
                String key = entry.getKey();
                IListener listener = entry.getValue();
                TableBO bo = cache.get(key);
                if (bo != null) {
                    Map<String, String> datas = listener.mappingColumn();
                    datas.forEach((k, v) ->
                            bo.addJaveTypeName(k, v)
                    );
                }
            }
    }


    public TableBO getTableBO(String dbName, String tableName) {
        return cache.get(BinLogUtils.createKey(dbName.toLowerCase(), tableName.toLowerCase()));
    }

}
