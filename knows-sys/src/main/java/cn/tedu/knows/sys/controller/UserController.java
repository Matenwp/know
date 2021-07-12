package cn.tedu.knows.sys.controller;


import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.sys.service.IUserService;
import cn.tedu.knows.sys.vo.RegisterVo;
import cn.tedu.knows.sys.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@Slf4j
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

    @PostMapping("/register")
    public String registerStudent(
            @Validated RegisterVo registerVo,
            BindingResult result) {
        if (result.hasErrors()) {
            //获得错误信息
            String msg = result.getFieldError().getDefaultMessage();
            return msg;
        }
        log.debug("收到学生注册信息:{}", registerVo);
        userService.registerStudent(registerVo);
        return "注册完成";
    }
}
