package cn.tedu.knows.portal.controller;


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
}
