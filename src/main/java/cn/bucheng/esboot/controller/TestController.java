package cn.bucheng.esboot.controller;

import cn.bucheng.esboot.model.vo.TeacherVO;
import cn.bucheng.esboot.model.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author ：yinchong
 * @create ：2019/7/26 14:01
 * @description：
 * @modified By：
 * @version:
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

    @RequestMapping("user")
    public String user(@Valid UserVO userVO) {
        log.info(userVO.toString());
        return "success";
    }

    @RequestMapping("teacher")
    public String teacher(@Valid TeacherVO teacherVO) {
        log.info(teacherVO.toString());
        return "success";
    }
}
