package cn.bucheng.esboot.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author ：yinchong
 * @create ：2019/7/19 8:57
 * @description：
 * @modified By：
 * @version:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "normal_bean", type = "normal_bean")
public class NormalBean {
    @Id
    private Long id;
    private String name;
    private String title;
    private String content;
}
