package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.UserVo;
import com.sun.org.apache.bcel.internal.generic.IUSHR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping("/master")
    public List<User> master(){
        List<User> users=userService.getTeachers();
        return users;
    }

    //获得用户信息面板数据的控制层方法
    @GetMapping("/me")
    public UserVo getUserVo(
            @AuthenticationPrincipal UserDetails user){
        UserVo userVo=
                userService.getCurrentUserVo(user.getUsername());
        return userVo;
    }
}
