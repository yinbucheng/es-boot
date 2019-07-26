package cn.bucheng.esboot.dao;

import cn.bucheng.esboot.model.entity.BookEntity;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author ：yinchong
 * @create ：2019/7/24 16:33
 * @description：
 * @modified By：
 * @version:
 */
public interface BookRepository extends ElasticsearchRepository<BookEntity, Long> {
    List<BookEntity> findByNameLike(String name);

    List<BookEntity> findByWriter(String author);

    BookEntity findByName(String name);

    List<BookEntity> findByContentLike(String content);

    @Query("{\"bool\" : {\"must\" : {\"bool\" : {\"should\" : [ {\"field\" : {\"name\" : \"?0*\",\"analyze_wildcard\" : true}},{\"field\" : {\"content\" : \"*?0*\",\"analyze_wildcard\" : true}},{\"field\" : {\"writer\" : \"?0*\",\"analyze_wildcard\" : true}}, {\"field\" : {\"title\" : \"?0*\",\"analyze_wildcard\" : true}} ]}}}}")
    List<BookEntity> findLikeAll(String param);

    @Query("{\"bool\" : {\"must\" : {\"field\" : {\"content\" : {\"query\" : \"?0\",\"analyze_wildcard\" : true}}}}}")
    List<BookEntity> findLikeContent(String content);
}
