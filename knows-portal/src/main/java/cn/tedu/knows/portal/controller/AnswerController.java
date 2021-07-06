package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.service.IAnswerService;
import cn.tedu.knows.portal.vo.AnswerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@RestController
@RequestMapping("/v1/answers")
@Slf4j
public class AnswerController {

    @Resource
    private IAnswerService answerService;

    //新增一个回答的方法
    // /v1/answers
    @PostMapping("")
    //回答问题只有讲师可以执行
    @PreAuthorize("hasRole('TEACHER')")
    public String addAnswer(
            @Validated AnswerVo answerVo,
            BindingResult result,
            @AuthenticationPrincipal UserDetails user){
        log.debug("收到信息:{}",answerVo);
        if(result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            return msg;
        }
        Answer answer=answerService
                .saveAnswer(answerVo,user.getUsername());
        return "答案已提交";
    }

}
