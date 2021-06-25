package cn.tedu.knows.portal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class SecurityTest {

    //加密测试
    @Test
    void encode(){
        //PasswordEncoder密码加密类型对象的接口
        //多数流行的加密规则都实现自这个接口
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        //将密码123456加密后返回加密结果
        String pwd=encoder.encode("123456");
        System.out.println(pwd);
        //每次加密的得到的密码不同,是当前加密算法的"随机加盐"技术
        //是为了提高密码加密的安全性
        //$2a$10$f7b5X9txC178W6fDF7rPOeUWHGdwI9uqmmL9/3DD7xt4GJnT0bQxS
    }

    //密码验证
    @Test
    void match(){
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        //调用matches方法验证一个字符串是否匹配一个加密结果
        //matches方法两个参数,1是明文字符串,2是加密字符串
        //返回这个明文字符串是否匹配加密字符串
        boolean b=encoder.matches("123456",
                "$2a$10$f7b5X9txC178W6fDF7rPOeUWHGdwI9uqmmL9/3DD7xt4GJnT0bQxS");
        System.out.println("验证结果:"+b);
    }





}
