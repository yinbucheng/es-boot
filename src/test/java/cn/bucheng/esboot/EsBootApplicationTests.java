package cn.bucheng.esboot;

import cn.bucheng.esboot.template.ESTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsBootApplicationTests {

    @Autowired
   private ESTemplateService templateService;

    @Test
    public void save(){
        templateService.save();
    }

    @Test
    public void list(){
        templateService.listAll();
    }

    @Test
    public void delete(){
        templateService.delete();
    }

    @Test
    public void update(){
        templateService.update();
    }

}
