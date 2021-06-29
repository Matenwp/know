package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String registerStudent(RegisterVo registerVo){
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
