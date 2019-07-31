package cn.bucheng.esboot.controller;

import cn.bucheng.esboot.dao.BookRepository;
import cn.bucheng.esboot.entity.BookEntity;
import cn.bucheng.esboot.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    @Autowired
    private BookMapper bookMapper;

    @RequestMapping("listAll")
    public Object listAll() {
        return bookRepository.findAll();
    }


    @Transactional
    @RequestMapping("save")
    public Object save() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void beforeCommit(boolean readOnly) {
                super.beforeCommit(readOnly);
//                throw new RuntimeException("test");
            }

            @Override
            public void beforeCompletion() {
//                super.beforeCompletion();
                throw new RuntimeException("test");
            }

            @Override
            public void afterCommit() {
                super.afterCommit();
            }

            @Override
            public void afterCompletion(int status) {
                super.afterCompletion(status);
            }
        });
        BookEntity bookEntity = new BookEntity();
        bookEntity.setContent("test for all");
        bookEntity.setCreateTime(new Date());
        bookEntity.setUpdateTime(new Date());
        bookEntity.setWriter("yinchong");
        bookEntity.setTitle("test");
        bookEntity.setName("hello");
        bookMapper.insert(bookEntity);
//        throw new RuntimeException("test");
        return "ok";
    }

}
