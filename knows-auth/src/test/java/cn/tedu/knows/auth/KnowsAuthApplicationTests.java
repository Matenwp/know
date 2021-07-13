package cn.tedu.knows.auth;

import cn.tedu.knows.auth.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.Resource;

@SpringBootTest
class KnowsAuthApplicationTests {

    @Resource
    UserDetailsServiceImpl userDetailsService;
    @Test
    void contextLoads() {
        UserDetails user=userDetailsService
                .loadUserByUsername("st2");
        System.out.println(user);
    }

}
