package cn.bucheng.esboot.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author ：yinchong
 * @create ：2019/7/26 10:52
 * @description：
 * @modified By：
 * @version:
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BookVO implements Serializable {
    @NotBlank(message = "名称不能为空")
    private String name;
    @NotBlank(message = "标题不能为空")
    private String title;
    @NotBlank(message = "内容不能为空")
    private String content;
    @NotBlank(message = "作者不能为空")
    private String writer;
}
