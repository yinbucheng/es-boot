package cn.bucheng.esboot.service.impl;

import cn.bucheng.esboot.entity.BookEntity;
import cn.bucheng.esboot.mapper.BookMapper;
import cn.bucheng.esboot.service.IBookService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author ：yinchong
 * @create ：2019/7/25 17:08
 * @description：
 * @modified By：
 * @version:
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, BookEntity> implements IBookService {
}
