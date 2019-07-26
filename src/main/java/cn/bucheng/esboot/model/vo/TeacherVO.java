package cn.bucheng.esboot.model.vo;

import cn.bucheng.esboot.annotation.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author ：yinchong
 * @create ：2019/7/26 13:57
 * @description：
 * @modified By：
 * @version:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherVO implements Serializable {
    @NotBlank(message = "名称不能为空")
    private String name;
    @Gender(message = "老师的性别有误")
    private String gender;
}
