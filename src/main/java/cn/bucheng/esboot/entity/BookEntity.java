package cn.bucheng.esboot.entity;

import cn.bucheng.mysql.annotation.ColumnName;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/7/24 16:31
 * @description：
 * @modified By：
 * @version:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(indexName = "book", type = "book", shards = 3, replicas = 1)
@TableName("ad_book")
@cn.bucheng.mysql.annotation.TableName(schema = "ad_test", table = "ad_book")
public class BookEntity implements Serializable {
    @Id
    @Field(type = FieldType.Long)
    @TableId(type = IdType.AUTO)
    private Long id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Keyword)
    private String title;
    @Field(type = FieldType.Keyword)
    private String writer;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    @ColumnName(sqlColumn = "create_time")
    private Date createTime;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    @ColumnName(sqlColumn = "update_time")
    private Date updateTime;
    @Field(type = FieldType.Text)
    private String content;


}
