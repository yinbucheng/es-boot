package cn.bucheng.esboot.binlog;

/**
 * @author ：yinchong
 * @create ：2019/7/24 19:09
 * @description：
 * @modified By：
 * @version:
 */
public class BinLogUtils {

    public static String createKey(String dbName,String tableName){
        return dbName+"-"+tableName;
    }
}
