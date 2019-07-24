package cn.bucheng.esboot.dao;

import cn.bucheng.esboot.entity.BookEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author ：yinchong
 * @create ：2019/7/24 16:33
 * @description：
 * @modified By：
 * @version:
 */
public interface BookRepository extends ElasticsearchRepository<BookEntity,Long> {
    BookEntity findByName(String name);
    List<BookEntity> findByWriter(String author);
}
