package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    //定义两个角色的常量,用于在方法中判断用户的角色
    public static final GrantedAuthority STUDENT=
            new SimpleGrantedAuthority("ROLE_STUDENT");
    public static final GrantedAuthority TEACHER=
            new SimpleGrantedAuthority("ROLE_TEACHER");

    @GetMapping(value = {"/","/index.html"})
    public String index(
            @AuthenticationPrincipal UserDetails user){
        //判断是不是学生
        if(user.getAuthorities().contains(STUDENT)){
            //等价于重定向方法
            //response.sendRedirect("/index_student.html")
            //如果是学生,重定向到学生首页
            return "redirect:/index_student.html";
        }else if(user.getAuthorities().contains(TEACHER)){
            //如果是讲师,重定向到讲师首页
            return "redirect:/index_teacher.html";
        }
        //不是学生也不是讲师跳转到的登录页
        return "redirect:/login.html";
    }



}
