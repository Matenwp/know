package cn.tedu.knows.faq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.tedu.knows.faq.mapper")
public class KnowsFaqApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsFaqApplication.class, args);
    }

    //将下面方法的返回值保存到Spring容器
    @Bean
    // 让这个服务间调用的对象支持负载均衡
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
