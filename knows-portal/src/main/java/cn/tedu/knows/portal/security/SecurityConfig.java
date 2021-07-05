package cn.tedu.knows.portal.security;

import cn.tedu.knows.portal.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    //设置授权范围的方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//禁用防跨域攻击功能
            .authorizeRequests()//开始进行授权范围管理
            .antMatchers(//设置如下url
                    //"/index_student.html",
                    "/js/*",
                    "/css/*",
                    "/img/**",
                    "/bower_components/**",
                    "/login.html",
                    "/register.html",
                    "/register")
                .permitAll()//上面设置的路径全部允许直接访问
            .anyRequest()//其它的路径
            .authenticated()//需要进行登录
            .and().formLogin()//如果登录使用表单验证
            .loginPage("/login.html")//自定义登录页面的路径
            .loginProcessingUrl("/login")//配置提交登录的路径
            .failureUrl("/login.html?error")//当登录失败时,跳转的页面
            .defaultSuccessUrl("/index.html")//登录成功时默认显示的页面
            .and().logout()//开始设置登出
            .logoutUrl("/logout")//设置登出路径
            .logoutSuccessUrl("/login.html?logout");//登出后显示的页面
    }
}
