package cn.bucheng.esboot.controller;

import cn.bucheng.esboot.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yinchong
 * @create ：2019/7/25 17:07
 * @description：
 * @modified By：
 * @version:
 */
@RestController
public class BookController {
    @Autowired
    private IBookService bookService;


}
