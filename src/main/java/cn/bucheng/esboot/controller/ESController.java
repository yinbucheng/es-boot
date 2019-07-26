package cn.bucheng.esboot.controller;

import cn.bucheng.esboot.dao.BookRepository;
import cn.bucheng.esboot.model.entity.BookEntity;
import cn.bucheng.esboot.model.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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
@Slf4j
public class ESController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ElasticsearchTemplate template;

    @RequestMapping("listAll")
    public Object listAll() {
        return bookRepository.findAll();
    }


    @RequestMapping("queryName")
    public List<BookEntity> queryLikeName(String name) {
        return bookRepository.findByNameLike(name);
    }

    @RequestMapping("queryAnd")
    public List<BookEntity> queryAndLike(String param) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("name", param))
                .must(QueryBuilders.termQuery("title", param))
                .must(QueryBuilders.termQuery("content", param))
                .must(QueryBuilders.termQuery("writer", param));
        log.info(builder.toString());
        SearchQuery query = new NativeSearchQuery(builder);
        return template.queryForList(query, BookEntity.class);
    }


    /**
     * termQuery("key", obj) 完全匹配
     * matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
     * fuzzyQuery("user", "kimch")  模糊查询,不能用通配符, 找到相似的
     * wildcardQuery("user", "ki*hy") 通配符查询, 支持 *
     *
     * @param param
     * @return
     */
    @RequestMapping("queryOr")
    public List<BookEntity> queryOrLike(String param) {
        QueryBuilder builder = QueryBuilders.fuzzyQuery("content", param);
//                .should(QueryBuilders.fuzzyQuery("name", param))
//                .should(QueryBuilders.fuzzyQuery("title", param))
//                .should(QueryBuilders.wildcardQuery("content", param+"*"))
//                .should(QueryBuilders.fuzzyQuery("writer", param));
       BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
       boolQueryBuilder.should(builder);
        log.info(boolQueryBuilder.toString());
        SearchQuery query = new NativeSearchQuery(boolQueryBuilder);
        log.info(query.getQuery().toString());
        return template.queryForList(query, BookEntity.class);
    }

    @RequestMapping("queryContent")
    public List<BookEntity> queryContentLike(String content) {
        return bookRepository.findByContentLike(content);
    }

}
