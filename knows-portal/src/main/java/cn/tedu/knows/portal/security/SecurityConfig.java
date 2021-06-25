package cn.tedu.knows.portal.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration这个注解表示当前类是Spring的配置类
@Configuration
//启动Spring-Security的框架的权限管理功能
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //参数auth就是管理Spring-Security进行登录和权限管理的核心对象
        //通过代码设置一个可以登录到Spring-Security的用户
        //注意一旦设置了代码,配置文件中的用户就失效了
        auth.inMemoryAuthentication()
                .withUser("jerry")
                .password("{bcrypt}$2a$10$f7b5X9txC178W6fDF7rPOeUWHGdwI9uqmmL9/3DD7xt4GJnT0bQxS")
                .authorities("add","delete");

    }
}
