package cn.tedu.knows.auth.security;

import cn.tedu.knows.commons.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username)
                                throws UsernameNotFoundException {
        //要想获得当前登录用户的详情
        //1.获得登录用户名对应的User对象
        String url="http://sys-service/v1/auth/user?username={1}";
        User user=restTemplate.getForObject(url,User.class,username);
        if(user==null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        //2.查询所有角色,权限
        //3.实例化一个auth数组
        //4.构建UserDetails对象
        //5.返回
        return null;
    }
}
