package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.vo.QuestionVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
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

    //编写新增问题的控制层方法
    //PostMapping("")表示/v1/questions就是新增问题的url
    @PostMapping("")
    public String createQuestion(
            @Validated QuestionVo questionVo,
            BindingResult result,
            @AuthenticationPrincipal UserDetails user){
        log.debug("接收到问题内容:{}",questionVo);
        if(result.hasErrors()){
            //如果验证出现问题,返回验证错误信息
            String msg=result.getFieldError().getDefaultMessage();
            return msg;
        }
        //调用业务逻辑层的方法
        try{
            questionService.saveQuestion(
                    questionVo,user.getUsername());
            return "问题已发布";
        }catch(ServiceException e){
            log.error("发布失败",e);
            return e.getMessage();
        }
    }

    //查询讲师首页的任务列表的控制器方法
    @GetMapping("/teacher")
    //这个注解会使Spring-Security框架自动判断当前登录用户是否有指定资格
    //指定登录用户必须包含ROLE_TEACHER这个身份
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public PageInfo teacher(
            @AuthenticationPrincipal UserDetails user,
            Integer pageNum){
        if(pageNum==null)
            pageNum=1;
        Integer pageSize=8;
        PageInfo<Question> pageInfo=questionService
                .getTeacherQuestions(user.getUsername(),
                        pageNum,pageSize);
        return pageInfo;
    }

    //根据问题id 查询问题详情
    // /v1/questions/153
    // 遇到上面这样的路径,没有直接匹配的控制器方法
    // 那么SpringMvc就会匹配使用{}占位的方法来调用
    @GetMapping("/{id}")
    //如果想获得由{id}占位符替代的数据
    //需要在控制方法中添加@PathVariable注解声明一个参数
    //这个参数的名称必须和{id}中{}里的标识符名称对应
    public Question question(
            @PathVariable Integer id){
        if(id==null)
            throw new ServiceException("ID不能为空");
        Question question=questionService.getQuestionById(id);
        return question;
    }





}
