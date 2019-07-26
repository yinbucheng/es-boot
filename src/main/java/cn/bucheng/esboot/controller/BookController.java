package cn.bucheng.esboot.controller;

import cn.bucheng.esboot.model.entity.BookEntity;
import cn.bucheng.esboot.model.vo.BookVO;
import cn.bucheng.esboot.service.IBookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author ：yinchong
 * @create ：2019/7/25 17:07
 * @description：
 * @modified By：
 * @version:
 */
@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private IBookService bookService;


    @RequestMapping("save")
    public String save(@RequestBody @Valid BookVO bookVO) {
        BookEntity entity = new BookEntity();
        BeanUtils.copyProperties(bookVO, entity);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        boolean row = bookService.insert(entity);
        if (row) {
            return "执行成功";
        } else {
            return "执行失败";
        }
    }

}
