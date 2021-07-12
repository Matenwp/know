package cn.tedu.knows.faq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.tedu.knows.faq.mapper")
public class KnowsFaqApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsFaqApplication.class, args);
    }

}
