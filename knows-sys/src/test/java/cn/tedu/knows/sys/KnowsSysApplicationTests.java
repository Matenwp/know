package cn.tedu.knows.sys;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KnowsSysApplicationTests {

    @Value("${my.name}")
    String name;
    @Value("${class.name}")
    String className;
    @Test
    void contextLoads() {
        System.out.println(name);
        System.out.println(className);
    }

}
