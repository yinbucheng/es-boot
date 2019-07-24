package cn.bucheng.esboot;

import cn.bucheng.esboot.dao.BookRepository;
import cn.bucheng.esboot.entity.BookEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBootApplicationTests {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(BookEntity.class);
    }


    @Test
    public void testDeleteIndex(){
        elasticsearchTemplate.deleteIndex(BookEntity.class);
    }

    @Test
    public void testSaveBook() {
        BookEntity book = new BookEntity();
        book.setContent("这是一次测试代码希望成功哦");
        book.setName("测试");
        book.setTitle("测试");
        book.setWriter("尹冲");
        book.setUpdateTime(new Date());
        book.setCreateTime(new Date());
        book.setId(10L);
        BookEntity result = bookRepository.save(book);
        System.out.println(result.getId());
    }

    @Test
    public void testListAll(){
        Iterable<BookEntity> all = bookRepository.findAll();
        if(ObjectUtils.isEmpty(all)){
            return;
        }

        Iterator<BookEntity> iterator = all.iterator();
        while(iterator.hasNext()){
            BookEntity next = iterator.next();
            System.out.println(next);
        }

    }

}
