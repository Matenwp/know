package cn.tedu.knows.portal.security;

import cn.tedu.knows.portal.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@Configuration这个注解表示当前类是Spring的配置类
@Configuration
//启动Spring-Security的框架的权限管理功能
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //从Spring容器中注入实现了Spring-Security登录要求的对象
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里的配置就是设置登录页面点击登录时
        //Spring-Security会自动调用这个方法
        //会获得这个类中loadUserByUsername方法的返回值
        //自动判断登录结果,并在登录页面上反馈
        auth.userDetailsService(userDetailsService);

    }
}
