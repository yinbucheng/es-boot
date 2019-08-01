package cn.bucheng.esboot.binlog.user;

import cn.bucheng.mysql.annotation.ColumnName;
import cn.bucheng.mysql.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/8/1 9:33
 * @description：
 * @modified By：
 * @version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(schema = "ad_test",table = "ad_user")
public class UserPO implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    @ColumnName(sqlColumn = "create_time")
    private Date createTime;
    @ColumnName(sqlColumn = "update_time")
    private Date updateTime;
}
