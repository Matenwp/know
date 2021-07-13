package cn.tedu.knows.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Security全部放行
    //因为我们学习的Oauth有新的验证方式,普通的验证就不需要了
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().formLogin();
    }
    //当前类其实已经转换为,为Oauth2提供帮助和辅助的类了
    //当前类中的父类里有一个对象,Oauth2需要,这里将它保存到Spring容器中
    @Bean
    public AuthenticationManager authenticationManagerBean()
                                            throws Exception {
        return super.authenticationManagerBean();
    }
    //保存到SpringBoot容器一个加密对象,授权过程中使用
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
