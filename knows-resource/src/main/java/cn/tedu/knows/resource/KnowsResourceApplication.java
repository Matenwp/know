package cn.tedu.knows.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class KnowsResourceApplication {

    public static void main(String[] args) {

        SpringApplication.run(KnowsResourceApplication.class, args);

    }

}
