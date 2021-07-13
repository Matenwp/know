package cn.tedu.knows.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class TokenConfig {

    //先将token保存到服务器内存中
    //下面是一个能生成token并保存到内存中的对象
    @Bean
    public TokenStore tokenStore(){
        return new InMemoryTokenStore();
    }

}
