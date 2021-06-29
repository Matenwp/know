package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
//@RequestMapping注解写在控制器类上
//效果是当前类所有请求都需要使用/v1/tags做前缀才能访问到
@RequestMapping("/v1/tags")
public class TagController {

    @Autowired
    private ITagService tagService;

    //下面的控制方法使用/v1/tags就可以访问
    @GetMapping("")
    public List<Tag> tags(){
        List<Tag> tags=tagService.getTags();
        return tags;
    }
}
