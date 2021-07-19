package cn.tedu.knows.search.controller;

import cn.tedu.knows.search.service.IQuestionService;
import cn.tedu.knows.search.vo.QuestionVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/v3/questions")
public class QuestionController {

    @Resource
    private IQuestionService questionService;

    @PostMapping
    public PageInfo<QuestionVo> search(String key, Integer pageNum,
                         @AuthenticationPrincipal UserDetails user){
        Integer pageSize=8;
        PageInfo<QuestionVo> pageInfo=
                questionService.search(key,user.getUsername(),
                                                pageNum,pageSize);
        return pageInfo;
    }


}
