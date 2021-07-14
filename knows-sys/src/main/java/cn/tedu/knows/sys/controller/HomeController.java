package cn.tedu.knows.sys.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

// ↓↓↓↓↓↓↓↓↓↓
@RestController
@Slf4j
// ↓↓↓↓↓↓↓↓↓↓
@RequestMapping("/v1/home")
public class HomeController {
    //定义两个角色的常量,用于在方法中判断用户的角色
    public static final GrantedAuthority STUDENT =
            new SimpleGrantedAuthority("ROLE_STUDENT");
    public static final GrantedAuthority TEACHER =
            new SimpleGrantedAuthority("ROLE_TEACHER");
    // ↓↓↓↓↓↓↓↓↓↓
    @GetMapping("")
    public String index(
            @AuthenticationPrincipal UserDetails user,
            String accessToken) {
        System.out.println(accessToken);
        //判断是不是学生
        if (user.getAuthorities().contains(STUDENT)) {
            // ↓↓↓↓↓↓↓↓↓↓
            return "/index_student.html";
        } else if (user.getAuthorities().contains(TEACHER)) {
            //如果是讲师
            // ↓↓↓↓↓↓↓↓↓↓
            return "/index_teacher.html";
        }
        //不是学生也不是讲师跳转到的登录页
        // ↓↓↓↓↓↓↓↓↓↓
        return "/login.html";
    }

}
