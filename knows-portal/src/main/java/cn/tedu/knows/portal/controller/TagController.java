package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@RestController
//@RequestMapping注解写在控制器类上
//效果是当前类所有请求都需要使用/v1/tags做前缀才能访问到
@RequestMapping("/v1/tags")
public class TagController {

    //下面的hello方法要想访问它的路径就是
    //localhost:8080/v1/tags/hello
    @GetMapping("/hello")
    public String welcome(){
        return "hello welcome!!!";
    }

    @GetMapping("/get")
    //下面的注解表示当前方法需要用户拥有特殊权限才能访问
    @PreAuthorize("hasAuthority('add')")
    public Tag getTag(){
        Tag t=new Tag();
        t.setName("乾坤大挪移");
        return t;
    }
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('delete')")
    public Tag getList(){
        Tag t=new Tag();
        t.setName("九阴白骨爪");
        return t;
    }


}
