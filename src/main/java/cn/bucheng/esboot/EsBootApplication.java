package cn.bucheng.esboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "cn.bucheng.esboot.dao")
@SpringBootApplication
public class EsBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsBootApplication.class, args);
    }

}
