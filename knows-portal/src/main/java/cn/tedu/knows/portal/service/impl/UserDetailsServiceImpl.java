package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    //UserDetails是Spring-Security框架提供的接口
    //其中可以保存用户登录需要的数据和权限信息
    //我们要想登录,必须获得这个对象,并在loadUserByUsername方法中返回
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //这个方法的参数是用户名,是Spring-Security框架会自动赋值的
        //1.首先根据用户名获得用户信息
        User user=userMapper.findUserByUsername(username);
        //2.检查用户是否存在
        if(user == null){
            //用户不存在,返回null,登录失败
            return null;
        }
        //3.根据用户id查询用户权限
        //4.将权限整理成String数组
        //5.构建UserDetails类型对象
        //6.返回UserDetails对象
        return null;
    }
}
