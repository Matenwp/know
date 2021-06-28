package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SystemController {

    @PostMapping("/register")
    public String registerStudent(RegisterVo registerVo){
        log.debug("收到学生注册信息:{}",registerVo);
        return "注册完成";
    }


}
