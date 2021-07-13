package cn.tedu.knows.sys.controller;

import cn.tedu.knows.commons.model.Permission;
import cn.tedu.knows.commons.model.Role;
import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.sys.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @GetMapping("/demo")
    public String demo(){
        return "sys:Hello World!!!";
    }

    @Resource
    private IUserService userService;
    @GetMapping("/user")
    public User getUser(String username){

        return userService.getUserByUserName(username);
    }

    @GetMapping("/permissions")
    public List<Permission> permissions(Integer id){
        return userService.getUserPermissionsById(id);
    }

    @GetMapping("/roles")
    public List<Role> roles(Integer id){
        return userService.getUserRolesById(id);
    }


}
