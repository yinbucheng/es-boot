package cn.bucheng.esboot.controller;

import cn.bucheng.esboot.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yinchong
 * @create ：2019/7/24 20:58
 * @description：
 * @modified By：
 * @version:
 */
@RestController
@RequestMapping("es")
public class ESController {
    @Autowired
    private BookRepository bookRepository;

    @RequestMapping("listAll")
    public Object listAll() {
        return bookRepository.findAll();
    }

    
}
