package cn.tedu.knows.faq.controller;


import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.faq.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/v2/tags")
public class TagController {

    @Autowired
    private ITagService tagService;

    //下面的控制方法使用/v2/tags就可以访问
    @GetMapping("")
    public List<Tag> tags(){
        List<Tag> tags=tagService.getTags();
        return tags;
    }
}
