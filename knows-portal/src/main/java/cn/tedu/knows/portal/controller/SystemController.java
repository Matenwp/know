package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class SystemController {

    @Resource
    private IUserService userService;

    @PostMapping("/register")
    public String registerStudent(
            //@Validated表示这个方法运行前
            //Spring Validation框架会对registerVo中的属性进行验证
            // 验证规则就是这个类中编写的规则
            @Validated RegisterVo registerVo,
            //BindingResult就是接收验证结果和错误信息的类型
            BindingResult result){
        //如果SpringValidation验证有错误
        if(result.hasErrors()){
            //获得错误信息
            String msg=result.getFieldError().getDefaultMessage();
            return msg;
        }
        log.debug("收到学生注册信息:{}",registerVo);
        try {
            userService.registerStudent(registerVo);
            return "注册完成";
        }catch(ServiceException e){
            //将异常信息输出到控制台
            log.error("注册失败",e);
            return e.getMessage();
        }

    }


}
