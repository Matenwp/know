package cn.tedu.knows.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class KnowsAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsAuthApplication.class, args);
    }

    //Auth授权必须有Ribbon
    @Bean
    //@LoadBalanced表示当前这个RestTemplate对象会按照
    //负载均衡的方法调用多个服务器
    @LoadBalanced
    public RestTemplate restTemplate(){
        //RestTemplate就是Ribbon提供的类型
        //它的对象就可以调用到其它微服务的方法
        return new RestTemplate();
    }
}
