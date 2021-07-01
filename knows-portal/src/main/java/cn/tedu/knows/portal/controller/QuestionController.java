package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.service.IQuestionService;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/v1/questions")
public class QuestionController {
    @Autowired
    private IQuestionService questionService;

    //根据当前登录学生,查询学生所有问题的控制器方法
    @GetMapping("/my")
    public PageInfo<Question> myQuestion(
            //@AuthenticationPrincipal是Spring-Security提供的注解
            //它能够将当前登录用户的详细信息赋值给紧随的参数
            @AuthenticationPrincipal UserDetails user,
            Integer pageNum){
        if(pageNum==null)
            pageNum=1;
        Integer pageSize=8;
        PageInfo<Question> pageInfo=
                questionService.getMyQuestions(
                        user.getUsername(),pageNum,pageSize);
        return pageInfo;
    }


}
