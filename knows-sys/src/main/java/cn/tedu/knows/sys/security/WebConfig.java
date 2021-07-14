package cn.tedu.knows.sys.security;

import cn.tedu.knows.sys.interceptor.AuthInterceptor;
import cn.tedu.knows.sys.interceptor.DemoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置跨域配置
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
        //如果实际开发中需要指定网关或前端项目能访问,其它不能访问,
        //就在allowedOrigins方法中指定允许的url就可以了
    }
    @Autowired
    private DemoInterceptor demoInterceptor;
    @Autowired
    private AuthInterceptor authInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(demoInterceptor)
                .addPathPatterns("/v1/home");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(
                        "/v1/home",
                        "/v1/users/me"
                );
    }
}
