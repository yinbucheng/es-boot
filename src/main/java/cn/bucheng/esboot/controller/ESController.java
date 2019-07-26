package cn.bucheng.esboot.controller;

import cn.bucheng.esboot.dao.BookRepository;
import cn.bucheng.esboot.model.entity.BookEntity;
import cn.bucheng.esboot.model.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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


    @RequestMapping("queryName")
    public List<BookEntity> queryLikeName(String name) {
        return bookRepository.findByNameLike(name);
    }

    @RequestMapping("query")
    public List<BookEntity> queryLike(String param){
        return bookRepository.findLikeAll(param);
    }

    @RequestMapping("queryContent")
    public List<BookEntity> queryContentLike(String content){
        return bookRepository.findByContentLike(content);
    }

}
