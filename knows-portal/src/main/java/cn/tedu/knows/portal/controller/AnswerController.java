package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.service.IAnswerService;
import cn.tedu.knows.portal.vo.AnswerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    public Answer addAnswer(
            @Validated AnswerVo answerVo,
            BindingResult result,
            @AuthenticationPrincipal UserDetails user){
        log.debug("收到信息:{}",answerVo);
        if(result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        //新增的代码↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
        Answer answer=answerService
                .saveAnswer(answerVo,user.getUsername());
        return answer;
    }

    //按问题id查询所有回答的控制层方法
    //   /v1/answers/question/153
    @GetMapping("/question/{id}")
    public List<Answer> getQuestionAnswers(
            @PathVariable Integer id){
        if(id==null){
            throw new ServiceException("问题id不能为空");
        }
        List<Answer> answers=answerService
                .getAnswersByQuestionId(id);
        return  answers;
    }





}
