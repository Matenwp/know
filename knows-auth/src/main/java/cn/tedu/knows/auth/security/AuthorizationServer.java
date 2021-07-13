package cn.tedu.knows.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
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
                .tokenServices(tokenService());
    }

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private ClientDetailsService clientDetailsService;
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
        services.setClientDetailsService(clientDetailsService);
        return services;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    // 设置客户端详情类似于用户详情
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //客户端id
                .withClient("gateway-client")
                //客户端秘钥
                .secret(passwordEncoder.encode("123456"))
                //设置权限
                .scopes("all")//all只是个名字而已和写abc效果相同
                //允许客户端进行的操作  里面的字符串千万不能写错
                .authorizedGrantTypes("password","refresh_token");
    }
    // 认证成功后的安全约束配置
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //认证通过后,允许客户端进行哪些操作
        security
                //公开oauth/token_key端点
                .tokenKeyAccess("permitAll()")
                //公开oauth/check_token端点
                .checkTokenAccess("permitAll()")
                //允许提交请求进行认证(申请令牌)
                .allowFormAuthenticationForClients();


    }
}
