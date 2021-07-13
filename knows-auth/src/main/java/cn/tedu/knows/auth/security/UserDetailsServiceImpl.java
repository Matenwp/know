package cn.tedu.knows.auth.security;

import cn.tedu.knows.commons.model.Permission;
import cn.tedu.knows.commons.model.Role;
import cn.tedu.knows.commons.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
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
        url="http://sys-service/v1/auth/permissions?id={1}";
        Permission[] ps=restTemplate.getForObject(
                url,Permission[].class,user.getId());
        url="http://sys-service/v1/auth/roles?id={1}";
        Role[] rs=restTemplate.getForObject(
                url,Role[].class,user.getId());
        //3.实例化一个auth数组
        String[] auth=new String[ps.length+rs.length];
        int i=0;
        for(Permission p :ps)
            auth[i++]=p.getName();
        for(Role r :rs)
            auth[i++]=r.getName();
        //4.构建UserDetails对象
        UserDetails u= org.springframework.security.core.userdetails
                .User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .accountLocked(user.getLocked()==1)
                .disabled(user.getEnabled()==0)
                .authorities(auth)
                .build();
        //5.返回,千万别忘了返回
        return u;
    }
}
