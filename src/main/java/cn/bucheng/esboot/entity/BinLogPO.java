package cn.bucheng.esboot.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/7/31 16:15
 * @description：
 * @modified By：
 * @version:
 */
@TableName("ad_binlog")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinLogPO implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String fileName;
    private Long position;
    private Date createTime;
    private Date updateTime;
    private String remark;
}
