package cn.tedu.knows.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
//下面注解表示当前类是配置Oauth2的核心配置类
//Spring Oauth2框架会实例化这个对象进行各种调用
@EnableAuthorizationServer
public class AuthorizationServer extends
                    AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    //构造方法
    //这个构造方法会在Oauth2框架内部需要这个类时调用
    public AuthorizationServer(){
    }

    //设置认证端点的配置(/oauth/token)
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //配置认证管理器
                .authenticationManager(authenticationManager)
                //验证用户的方法获得用户详情
                .userDetailsService(userDetailsService)
                //要求提交认证使用post请求方式,提高安全性
                .allowedTokenEndpointRequestMethods(HttpMethod.POST)
                //要配置令牌的生成,由于令牌生成比较复杂,下面有方法实现
                .tokenServices(null);
    }

    @Autowired
    private TokenStore tokenStore;
    //这个方法会在上面方法中调用
    //可以尝试将@Bean去掉看影响
    @Bean
    public AuthorizationServerTokenServices tokenService(){
        //这个方法的目标就是获得一个令牌生成器
        DefaultTokenServices services=new DefaultTokenServices();
        //支持令牌刷新策略
        services.setSupportRefreshToken(true);
        //设置令牌生成策略
        services.setTokenStore(tokenStore);
        //设置令牌有效期
        services.setAccessTokenValiditySeconds(3600);//1小时
        services.setRefreshTokenValiditySeconds(3600*72);//3天
        //配置客户端详情
        return services;
    }

    // 设置客户端详情类似于用户详情
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    }
    // 认证成功后的安全约束配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    }
}
