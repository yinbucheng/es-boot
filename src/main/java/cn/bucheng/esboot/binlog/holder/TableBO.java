package cn.bucheng.esboot.binlog.holder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：yinchong
 * @create ：2019/7/24 19:17
 * @description：
 * @modified By：
 * @version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableBO {
    private String dbName;
    private String tableName;
    private Map<Integer, String> columnIdNameMaps = new HashMap<>(30);
    private Map<String, String> columnTypeNameMaps = new HashMap<>(30);


    public void addColumnIdName(Integer id, String name) {
        columnIdNameMaps.put(id, name);
    }

    public String getColumnName(Integer id) {
        return columnIdNameMaps.get(id);
    }

    public String getJavaTypeName(String columnName) {
        return columnTypeNameMaps.get(columnName);
    }

    public void addJaveTypeName(String columnName, String typeName) {
        columnTypeNameMaps.put(columnName, typeName);
    }

    public String getJavaName(int id) {
        String s = columnIdNameMaps.get(id);
        return columnTypeNameMaps.get(s);
    }

}
