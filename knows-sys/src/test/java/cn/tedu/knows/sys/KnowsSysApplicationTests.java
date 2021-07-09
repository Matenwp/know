package cn.tedu.knows.sys;

import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class KnowsSysApplicationTests {

    /*@Value("${my.name}")
    String name;
    @Value("${class.name}")
    String className;*/
    @Test
    void contextLoads() {
        /*System.out.println(name);
        System.out.println(className);*/
    }

    @Resource
    UserMapper userMapper;
    @Test
    void getUser(){
        User user=userMapper.findUserByUsername("st2");
        System.out.println(user);
    }








}
