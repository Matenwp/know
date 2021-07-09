package cn.tedu.knows.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.tedu.knows.sys.mapper")
public class KnowsSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsSysApplication.class, args);
    }

}
