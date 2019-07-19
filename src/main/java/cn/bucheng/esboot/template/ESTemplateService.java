package cn.bucheng.esboot.template;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：yinchong
 * @create ：2019/7/19 8:58
 * @description：
 * @modified By：
 * @version:
 */
@Service
@Slf4j
public class ESTemplateService {
    @Autowired
    private ElasticsearchTemplate template;


    public void save() {
        NormalBean bean = new NormalBean();
        bean.setContent("this is test for hello word");
        bean.setName("test1");
        bean.setId(11L);
        bean.setTitle("test for one");
        IndexQuery indexQuery = new IndexQueryBuilder().withId(bean.getId()+"").withObject(bean).build();
        template.index(indexQuery);
    }

    public void listAll() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchAllQuery()).build();
        List<NormalBean> normalBeans = template.queryForList(searchQuery, NormalBean.class);
        log.info(normalBeans + "");
    }

    public void delete() {
        DeleteQuery query = new DeleteQuery();
        query.setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("id", 1)));
        template.delete(query, NormalBean.class);
    }


    public void update() {
//        NormalBean bean = new NormalBean();
//        bean.setContent("this is test for hello word");
//        bean.setName("test1");
//        bean.setId(2L);
//        bean.setTitle("test for one");
//        UpdateRequest request = new UpdateRequest();
//        request.doc(bean);
//        UpdateQuery updateQuery = new UpdateQueryBuilder().withClass(NormalBean.class).withUpdateRequest(request).build();
//        template.update(updateQuery);

        NormalBean bean = new NormalBean();
        bean.setContent("this is test for hello word 2 ");
        bean.setName("test2");
        bean.setId(1L);
        bean.setTitle("test for two");
        IndexQuery indexQuery = new IndexQueryBuilder().withIndexName("normal_bean").withType("normal_bean").withId(bean.getId()+"").withObject(bean).build();
        template.index(indexQuery);
    }

}
