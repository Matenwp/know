package cn.tedu.knows.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan是MyBatis的注解
//功能是指定一个包中所有的接口是MyBatis框架的mapper接口
// 这样这个包中所有接口就不需要每个都使用@Mapper标记了
@MapperScan("cn.tedu.knows.portal.mapper")
public class KnowsPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsPortalApplication.class, args);
    }

}
